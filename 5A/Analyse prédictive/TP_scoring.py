import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC
from sklearn.metrics import confusion_matrix
from sklearn.metrics import recall_score
from sklearn.metrics import precision_score
from sklearn.metrics import roc_curve
from sklearn.metrics import auc
from random import randint

import matplotlib.pyplot as plt

if __name__ == "__main__":
    file = "wave5000.data"
    data = pd.read_csv(file, header=None)
    #On mélange le dataset
    data.sample(frac=1).reset_index(inplace=True, drop=True)

    train, test = train_test_split(data, stratify=data.iloc[:, 21], train_size=0.8)
    clf = DecisionTreeClassifier(min_samples_split=20)

    #classe équilibrée ? Oui

    nb_features = len(data.columns) - 1
    train_x = train.values[:, :nb_features]
    train_y = train.values[:, -1]
    test_x = test.values[:, :nb_features]
    test_y = test.values[:, -1]
    clf.fit(train_x, train_y)
    predict = clf.predict(test_x)

    taux_reussite = sum(predict == test_y) / len(predict)
    print("Taux de réussite : {}".format(taux_reussite))

    #On met les classes autres que 1 à 0
    data[data[21] == 2] = 0

    #On apprend à nouveau
    train, test = train_test_split(data, stratify=data.iloc[:, 21], train_size=0.8)
    train_x = train.values[:, :nb_features]
    train_y = train.values[:, -1]
    test_x = test.values[:, :nb_features]
    test_y = test.values[:, -1]
    clf = DecisionTreeClassifier(min_samples_split=20)
    clf.fit(train_x, train_y)
    predict = clf.predict(test_x)

    conf_matrix = confusion_matrix(y_true=test_y, y_pred=predict)
    tn, fp, fn, tp = conf_matrix.ravel()
    confiance = tp / (tp + fp)
    rappel = tp / (tp + fn)
    sensibilite = tp / (tp + fn)
    specificite = tn / (tn + fp)

    #pour vérifier
    sklearn_rappel = recall_score(y_true=test_y, y_pred=predict)
    sklearn_precision = precision_score(y_true=test_y, y_pred=predict)
    print(sklearn_rappel, sklearn_precision)

    #courbe roc
    probas_train = clf.predict_proba(train_x)
    probas_test = clf.predict_proba(test_x)
    fpr_train, tpr_train, _ = roc_curve(y_true=train_y, y_score=probas_train[:,1])
    fpr_test, tpr_test, _ = roc_curve(y_true=test_y, y_score=probas_test[:,1])
    roc_auc_train = auc(fpr_train, tpr_train)
    roc_auc_test = auc(fpr_test, tpr_test)
    plt.title("Roc curve")
    plt.plot(fpr_train, tpr_train, 'g', label='Train, AUC = %0.2f' % roc_auc_train)
    plt.plot(fpr_test, tpr_test, 'b', label='Test, AUC = %0.2f' % roc_auc_test)
    plt.legend(loc='lower right')
    plt.plot([0, 1], [0, 1], 'r--')
    plt.xlim([0, 1])
    plt.ylim([0, 1])
    plt.ylabel('True Positive Rate')
    plt.xlabel('False Positive Rate')
    plt.show()

    #Comparaison modèles
    clf1 = MLPClassifier()
    clf2 = GaussianNB()
    clf3 = LogisticRegression()
    clf4 = SVC(probability=True)
    clfs = [clf, clf1, clf2, clf3, clf4]

    colors = []
    for i in range(len(clfs)):
        colors.append('%06X' % randint(0, 0xFFFFFF))


    #fit, compute probas, compute roc and plot
    plt.title("Roc curve comparison")
    for index, clf_ in enumerate(clfs):
        clf_.fit(train_x, train_y)
        probas = clf_.predict_proba(test_x)
        fpr, tpr, _ = roc_curve(y_true=test_y, y_score=probas[:,1])
        auc_ = auc(fpr, tpr)
        plt.plot(fpr, tpr, label="{}, AUC = {:.3f}".format(type(clf_).__name__, auc_))
    plt.legend(loc='lower right')
    plt.plot([0, 1], [0, 1], 'r--')
    plt.xlim([0, 1])
    plt.ylim([0, 1])
    plt.ylabel('True Positive Rate')
    plt.xlabel('False Positive Rate')
    plt.show()
