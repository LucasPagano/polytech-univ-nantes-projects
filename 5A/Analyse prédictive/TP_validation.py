#using python 3.6
import itertools
import numpy as np
import pandas as pd
from sklearn.svm import SVC
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import GaussianNB

def split_to_train_test(df, train_frac=0.8):
    """Fonction pour split un dataframe en test et train et garder la proportion entre classes"""
    nb_features = len(df.columns) - 1
    train_df, test_df = pd.DataFrame(), pd.DataFrame()
    labels = df[nb_features].unique()
    for lbl in labels:
        lbl_df = df[df[nb_features] == lbl]
        lbl_train_df = lbl_df.sample(frac=train_frac)
        lbl_test_df = lbl_df.drop(lbl_train_df.index)
        train_df = train_df.append(lbl_train_df)
        test_df = test_df.append(lbl_test_df)

    return train_df, test_df

def split_val_croisee(df, nb_groupes=10):
    """Fonction pour split un dataframe en k groupes équilibrés sur la classe"""
    nb_features = len(df.columns) - 1

    df_split = {i:pd.DataFrame() for i in range(nb_groupes)}

    labels = df[nb_features].unique()
    for lbl in labels:
        lbl_df = df[df[nb_features] == lbl]
        splits = np.array_split(lbl_df, nb_groupes)
        for index, split in enumerate(splits):
            df_split[index] = df_split[index].append(split)

    return df_split



def validation_repetee(df, nombre_repetitions, clf):

    scores = []
    nb_features = len(df.columns) - 1

    for i in range(nombre_repetitions):
        train, test = split_to_train_test(df)

        train_x = train.values[:, :nb_features]
        train_y = train.values[:, -1]
        test_x = test.values[:, :nb_features]
        test_y = test.values[:, -1]

        clf.fit(train_x, train_y)

        predict = clf.predict(test_x)
        #précision
        taux_reussite = sum(predict == test_y) / len(predict)
        scores.append(taux_reussite)

    average = sum(scores)/len(scores)
    std = np.std(scores)

    return average, std

def leave_one_out(df, clf):

    nb_features = len(df.columns) - 1
    scores = []
    # À chaque itération, on choisit la ligne comme ensemble de test, et le reste comme ensemble d'entraînement
    for i in range(len(df)):
        test = df.iloc[i]
        train = df.drop(i)

        train_x = train.values[:, :nb_features]
        train_y = train.values[:, -1]
        test_x = test.values[:, :nb_features].reshape(1,-1)
        test_y = test.values[-1]

        clf.fit(train_x, train_y)

        predict = clf.predict(test_x)
        # précision
        taux_reussite = sum(predict == test_y) / len(predict)
        scores.append(taux_reussite)

    average = sum(scores) / len(scores)
    std = np.std(scores)

    return average, std

def validation_croisee(df, nb_groupes, clf):

    nb_features = len(df.columns) - 1

    #séparation du df en nb_groupes groupes
    d_split = split_val_croisee(df, nb_groupes)
    scores = []

    for group in range(nb_groupes):
        test = d_split[group]
        train = df.drop(test.index).reset_index(drop=True)
        train_x = train.values[:, :nb_features]
        train_y = train.values[:, -1]
        test_x = test.values[:, :nb_features]
        test_y = test.values[:, -1]

        clf.fit(train_x, train_y)

        predict = clf.predict(test_x)
        # précision
        taux_reussite = sum(predict == test_y) / len(predict)
        scores.append(taux_reussite)

    average = sum(scores) / len(scores)
    std = np.std(scores)

    return average, std

def validation_croisee_imbriquee(df, nb_groupes_internes, nb_groupes_externes):
    nb_features = len(df.columns) - 1

    # séparation du df en nb_groupes groupes
    df_split = split_val_croisee(df, nb_groupes_externes)
    out_averages = []
    best_model_params = []
    for index, df_test in df_split.items():
        in_averages = []
        #On construit le jeu de validation
        df_val = df.drop(df_test.index).reset_index(drop=True)

        for (model, params) in MODELS_HPP:
            clf = model(**params)
            average, std = validation_croisee(df_val, nb_groupes_internes, clf=clf)
            in_averages.append(average)

        best_average_index = np.argmax(in_averages)
        best_model, best_params = MODELS_HPP[best_average_index]
        best_classifier = best_model(**best_params)
        # On entraîne le modèle sur toutes les données de validation
        train_x = df_val.values[:, :nb_features]
        train_y = df_val.values[:, -1]
        test_x = df_test.values[:, :nb_features]
        test_y = df_test.values[:, -1]

        best_classifier.fit(train_x, train_y)
        predict = best_classifier.predict(test_x)
        # précision
        taux_reussite = sum(predict == test_y) / len(predict)
        out_averages.append(taux_reussite)
        best_model_params.append((best_model.__name__, best_params))

    average = sum(out_averages) / len(out_averages)
    std = np.std(out_averages)
    print("Best models used for average in nested cross validation : \n {}".format(best_model_params))
    return average, std

if __name__ == "__main__":
    file = "wave5000.data"
    df_ = pd.read_csv(file, header=None)
    #on mélange le dataframe
    df_.sample(frac=1).reset_index(inplace=True, drop=True)

    MODELS = [MLPClassifier, SVC, DecisionTreeClassifier, LogisticRegression, GaussianNB]
    PARAMETERS = [[{"hidden_layer_sizes" : d} for d in [(10,10), (20,20), (20,10), (10,20)]],
                  [{"kernel": "linear"}, {"kernel": "rbf"}, {"kernel": "sigmoid"}, {"kernel": "poly"}],
                  [{"criterion":"gini"}, {"criterion":"entropy"}],
                  [{}],
                  [{}]
                  ]

    MODELS_HPP = []
    for i in range(len(MODELS)):
        MODELS_HPP.extend(list(itertools.product([MODELS[i]], PARAMETERS[i])))

    clf_ = MLPClassifier()
    # precision, robustesse = validation_repetee(df_, nombre_repetitions=10, clf=clf_)
    # print("Précision (taux de réussite moyen) : {}\n"
    #       "Robustesse (écart-type du taux de réussite) : {}".format(precision, robustesse))
    # precision, robustesse = validation_croisee(df_, nb_groupes=10, clf=clf_)
    # print("Précision (taux de réussite moyen) : {}\n"
    #       "Robustesse (écart-type du taux de réussite) : {}".format(precision, robustesse))
    precision, robustesse = validation_croisee_imbriquee(df_, nb_groupes_internes=3, nb_groupes_externes=5)
    print("Précision (taux de réussite moyen) : {}\n"
          "Robustesse (écart-type du taux de réussite) : {}".format(precision, robustesse))
    precision, robustesse = leave_one_out(df_, clf_)
    print("Précision (taux de réussite moyen) : {}\n"
          "Robustesse (écart-type du taux de réussite) : {}".format(precision, robustesse))