import pandas as pd
import matplotlib
import matplotlib.pyplot as plt
from matplotlib import rcParams
import seaborn as sns

if __name__ == "__main__":
    #Import des données
    playlists = pd.read_csv("playlists.data", sep="\t")
    tracks = pd.read_csv("tracks.data", sep="\t")
    playlists_index = playlists.set_index("url")
    tracks_index = tracks.set_index("url")

    #0$
    playlists["title"] = playlists["title"].str.encode("ascii").str.decode("unicode-escape")
    playlists["artists"] = playlists["artists"].str.encode("ascii").str.decode("unicode-escape")

    #several artists become several lines
    aaa = playlists.artists.str.split(",").apply(pd.Series)
    aaa.index = [playlists.url, playlists.date, playlists.playlist, playlists.title, playlists.position]
    aaa = aaa.stack().reset_index()
    aaa.drop("level_5", axis=1, inplace=True)
    playlists = aaa.rename(index=str, columns={"0" : "artists"})


    #2.1.1 : Préparation des données
    #position pic
    pic_position = playlists.groupby(by=["url", "playlist"], sort=False, as_index=False, group_keys=False).agg({"position":'min'})
    pic_position.rename(columns = {"position":"pic_position"}, inplace=True)

    #indicateur binaire top position
    pic_position["binary_15"] = (pic_position["pic_position"] <= 15)*1

    #Durée en nombre de semaines
    nb_semaines = playlists.groupby(by=["url", "playlist"], sort=False, as_index=False).agg({"date": 'count'})
    nb_semaines.rename(columns = {"date":"nb_weeks"}, inplace=True)

    #Position moyenne
    mean_weeks = playlists.groupby(by=["url", "playlist"], sort=False, as_index=False).agg({"position": 'mean'})
    mean_weeks.rename(columns = {"position":"mean_position"}, inplace=True)

    #indicateur binaire position moyenne
    mean_weeks["binary_15_mean"] = (mean_weeks["mean_position"] <= 15)*1

    #2.1.2 : Analyse exploratoire

    #1D
    variables = tracks.columns.tolist()
    variables.remove("url")
    categorical_variables = ["Key", "Mode"]
    num_variables = [i for i in variables if i not in categorical_variables]
    playlists_tracks = pd.merge(playlists, tracks, on="url")

    # # Boxplots
    playlist_names = playlists["playlist"].unique()
    # all_columns_but_bpm = [i for i in playlists_tracks.columns.tolist() if i != "BPM" and (i in tracks.columns.tolist() or i == "playlist")]
    # fig, ax = plt.subplots()
    # playlists_tracks[all_columns_but_bpm].boxplot(by="playlist", figsize=(10,10), ax=ax)
    # fig.suptitle("Boxplot grouped by playlist", y=1)
    # plt.show()
    #
    # # Histogrammes
    # for i, playlist_name in enumerate(playlist_names):
    #     data = playlists_tracks.loc[playlists_tracks["playlist"] == playlist_name]
    #     fig, ax = plt.subplots()
    #     data.hist(ax=ax)
    #     fig.suptitle(playlist_name, y = 1)
    # plt.show()

    # playlists_tracks.boxplot(column="BPM", by="playlist")
    # plt.show()

    #TODO : key mode

    #2D

    # corr = playlists_tracks[num_variables].corr()
    # sns.pairplot(playlists_tracks, hue="playlist")
    # Valeurs intéressantes : accousticness-energy et valence-energy




    # #Chanson moyenne
    # tracks_playlist = tracks_index.join(playlists_index["playlist"])
    # mean_songs = tracks_playlist.groupby(by="playlist").mean()
    #
    # #no care : just need playlist now that mean is computed
    # tracks_playlist_unique = tracks_playlist[~tracks_playlist.duplicated(keep="first")]
    #
    # #On considère que le plus proche est celui qui minimise les écarts au carré
    # for playlist_name in playlist_names:
    #     mean_song = mean_songs.loc[playlist_name][num_variables]
    #     playlist_songs = tracks_playlist_unique[tracks_playlist_unique["playlist"] == playlist_name][num_variables]
    #     diff = (playlist_songs.sub(mean_song, axis=1)**2).sum(axis=1)
    #     print("5 Chansons les plus proches pour le style {} : {}".format(playlist_name, diff.nsmallest(5).index.tolist()))


    ## Chanson mieux classée par playlist
    # mean_weeks_title = playlists.groupby(by=["url", "playlist"], sort=False, as_index=False).agg({"position": 'mean', "title" : "min"})
    # for playlist in playlist_names:
    #     tmp = mean_weeks_title[mean_weeks["playlist"] == playlist]
    #     best_song = tmp.loc[tmp["position"].idxmin()]
    #     print("Playlist {}, best average position : {} Song : {} / {}".format(playlist, best_song["position"], best_song["title"], best_song["url"]))


    ## Artiste mieux classé par playlist
    # mean_weeks_title_artist = playlists.groupby(by=["playlist", "artists"], sort=False, as_index=False).agg({"position": 'mean'})
    # for playlist in playlist_names:
    #     tmp = mean_weeks_title_artist[mean_weeks_title_artist["playlist"] == playlist]
    #     best_song_t = tmp.loc[tmp["position"].idxmin()]["artist"]
    #     best_song_u = tmp.loc[tmp["position"].idxmin()]["url"]
    #     print("Best song on average for {} : {} / {}".format(playlist, best_song_t, best_song_u))
    #

    ## Évolution temporelle position chansons d'un artiste
    # artist = "Tim Dup"
    # artist = "Brigitte"
    #
    # songs_from_artist = playlists[playlists["artists"] == artist]
    # sns.lineplot(data=songs_from_artist, x="date", y="position", hue="title", sort=True).set_title("Position of {}'s songs".format(artist))
    # plt.show()

    ## Évolution temporelle 10 variables
    # pl = "fr"
    # tracks_playlist_temp = tracks_index.join(playlists_index[["playlist", "date"]])
    # tracks_playlist_temp = tracks_playlist_temp[tracks_playlist_temp["playlist"] == pl]
    # tracks_playlist_temp.groupby("date").mean()
    # for variable in num_variables:
    #     sns.lineplot(data=tracks_playlist_temp, x="date", y=variable).set_title("{} : temporal mean for {}".format(pl, variable))
    #     plt.show()
    #
    # ## Évolution temporelle taille
    # # groupby count + sélection
    # nb_semaines = playlists.groupby(by=["playlist", "date"], sort=False, as_index=False).count()[["playlist", "date", "index"]].rename(columns={"index": "taille"})
    # sns.lineplot(data=nb_semaines, x="date", y="taille", hue="playlist").set_title("Évolution temporelle de la taille des playlists")
    # plt.show()