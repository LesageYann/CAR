LESAGE Yann & PETIT Antoine

TP 1 : Implémentation d'un serveur FTP en Java

I) Introduction


		L'objectif du TP était d'implanter un serveur FTP en Java qui devait fonctionner avec un client FTP (tellnet, ftp, Filezilla, etc)

	Il devait au moins être capable de gérer les commandes suivantes : USER, PASS, RETR, STOR, LIST, QUIT, PWD, CWD, et CDUP.

	De plus le serveur devait être capable de gérer plusieurs connections à la fois grâce à des threads différents.


II) Le programme


		Pour executer le programme il faut utiliser la commande suivante : java -jar Serveur.jar <chemin du dossier dans lequel l'utilisateur se connectera sur le serveur FTP>

	Le serveur écoute sur le port 1666 et attend une connexion sur ce dernier. Une fois la connexion établie le Serveur créé un nouveau Thread qui sera chargé de s'occuper du client qui vient de se connecter.

	Ensuite il se met à nouveau à écouter sur le port 1666 en attente d'un nouveau client.

	Le Thread créé est un FTPRequest, ce dernier est chargé de gérer toutes les commandes que le client lui envoie, jusqu'à ce que ce dernier décide de quitter la connexion.


	Notre programme est capable de fonctionner avec la commande tellnet, la commande ftp et avec Filezilla.

III) Les commandes 

	USER <Nom de l'utilisateur> : Permet d'authentifier l'utilisateur, cette action est nécessaire avant toute autres actions sur le serveur

	PASS <Mot de passe de l'utilisateur> : Permet de finaliser l'authentification de l'utilisateur, cette action est nécessaire avant toute autres actions sur le serveur

	QUIT : Indique que le client souhaite terminer la connexion avec le serveur

	Une fois Authentifié :

		RETR <nom du fichier> : Récupère le fichier distant qui porte le nom du fichier dans le répertoire courant distant

		STOR <nom du fichier> : Dépose le fichier local du répertoire courant qui porte le nom du fichier dans le répertoire distant

		LIST : Demande au serveur de donner une description des fichiers et dossiers du répertoire courant distant

		PWD : Demande au serveur de donner la valeur du répertoire courant distant

		CWD <chemin> : Demande au serveur de faire du chemin le nouveau répertoire courant

		CDUP : Permet d'accéder au répertoire parent


IV) Architecture 

	1) Les classes 

		Serveur : La classe principale de l'application, elle contient le main. 
			C'est cette dernière qui va tourner en boucle en attendant que des clients se connectent. A chaque nouvelle connexion la classe créé un nouveau thread : FTPRequest

		FTPRequest : La classe gérant l'ensemble des commandes citer ci-dessus. 
			Elle gère aussi les connexions supplémentaires si nécessaire (mode passif/ actif) pour les commandes STOR, RETR et LIST.
		
		NativeCMD : Classe abstraite nous permettant de poser les bases pour avoir d'autres classes gérant les intérogations au system ( OS ou logiciel).
			Les classes l'implementant gardent connaissance dans quel état est le client afin de ne pas lui permettre d'utiliser des commandes qui lui serait interdit. Elles soulèront des erreurs le cas échéant.
	
		MapCMD : Classe héritant de NativeCMD. Implémente un système d'authentification hardcoder. ne dépend pas l'OS

		Linux CMD : Classe héritant de NativeCMD. Implémente des appels système à linux. Compatible uniquement linux. 
			Elle n'est pas complète car il s'agit surtout d'un POC pour montrer que notre serveur ftp est déclinable sur différent OS ou système logiciel.

	2 ) Gestion des erreurs 

		L'ensemble des exceptions déclenchées, que ce soit par l'ouverture de socket, ou lié à l'écriture dans le socket et la lecture dans le socket, ont été capturées pour envoyer des messages d'erreur dans les logs du serveur.
		Les erreurs lié aux commandes utilisateurs sont capturés dans FTPRequest et envoyés au client sous forme de message "5XX message d'erreur"

	3) Tests

		La classe FTPRequestTest contient des tests unitaires réalisés à l'aide de JUnit 4 et Mockito.
		Grâce à ce dernier nous avons pu vérifier les bons renvois de la part de la méthode processRequest ainsi que l'enchainement de quelques commandes.
		Le fait de ne pas avoir fait de factory pour ouvrir le cannal data, et donc créer un second socket, nous empêche de tester de maniére unitaire les méthodes STOR, RETR et LIST. En effet nous sommes dépendant du serveur(même si c'est local).
