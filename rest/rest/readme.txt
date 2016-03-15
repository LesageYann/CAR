LESAGE Yann & PETIT Antoine

TP 2 : Passerelle REST

I) Introduction


		L'objectif du TP était d'implanter une passerelle REST servant d'interface à un serveur FTP.

		Elle doit utiliser toutes les capacités que fournis REST avec les différents verbes : GET, PUT, DELETE, POST


II) Le programme


		Pour executer le programme il faut utiliser la commande suivante : java -jar Starter.jar 

	Une fois ceci fait la passerelle est accessible à l'adresse suivante : localhost:8080/rest/tp2/ftp/

	A partir de cette adresse un lien doit vous être proposez car vous n'êtes pas encore authentifié à un serveur FTP.

	En suivant ce dernier vous aurez un formulaire de connexion, après l'avoir rempli vous pourrez cliquer sur le lien redirigeant vers la racine.

	A partir de ce moment vous pouvez naviguer parmis les fichiers du serveur FTP.

	Un dossier sera indiqué par le lien "Open", cliquer sur ce dernier permet d'accéder au contenu de ce dossier.

	Un fichier est indiqué par le lien "Download", cliquer sur ce dernier permet de télécharger ce dernier.

	En plus de ces liens on trouve la Croix qui permet de supprimer un fichier.

	/!\ Ajouter description permettant de POST /!\

III) Les verbes et leurs comportements 

	GET <Sur un dossier>: Fera appel à LIST du serveur FTP afin de connaitre tout les fichiers dans le dossier

	GET <download/Sur un fichier>: Télécharge le fichier sur le poste grâce à RETR

	DELETE <Sur un fichier> : Supprime le fichier sur le serveur FTP grâce à DELETE (Remarque : ne fonctionne pas partout)

	PUT <Un fichier local> : Envoie le fichier sur le serveur FTP grâce à STOR, les dossiers necessaires sont crées (non implementer)

	POST <Un repertoire local> : Envoie le fichier sur le serveur FTP grâce à STOR. Le nom de fichier et le fichier sont passés via un form  les dossiers necessaires sont créés

IV) Architecture 

	1) Les classes 

		Starter : La classe permettant de lancer la passerelle REST.
			C'est la classe contenant le main.

		Config : La classe initialisant la configuration de la passerelle REST.
			Elle gère aussi les connexions supplémentaires si nécessaire (mode passif/ actif) pour les commandes STOR, RETR et LIST.
		
		FTPRessource : La classe contenant la gestion de nos verbes pour faire le lien avec un serveur FTP.
			Cette classe contient toutes les méthodes permettant de séparer les comportements selon les verbes.

		Page : Interface d'une page, doit contenir une méthode getPage

		HomePage : La page home lorsqu'on est pas authentifié

		AuthPage : La page d'authentification pour s'authentifier

		GetFTPPage : La page représentant les dossiers.

	2 ) Gestion des erreurs 

		L'ensemble des IOException sont récupéré au sein des méthodes et affichées dans les logs de la passerelle.
		On renvoie des indication html (ex mauvais login/password + formulaire de connexion) en guise d'indication pour l'utilisateur

	3) Tests

		Non fait, les tests ont seulement étaient effectué à la main.

	4) Difficultés

		Nous avons rencontrés des soucis avec les verbes DELETE et PUT qui n'ont pas été maintenu avec HTML5