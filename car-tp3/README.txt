LESAGE Yann & PETIT Antoine

TP 3 : – Akka – Transfert de don

I) Introduction


		L'objectif du TP était d'implanter une application Akka fonctionnant avec un système d'acteur de message. Les acteurs peuvent communiquer entre eux uniquement par messages.

	Nous avons fait le choix que le noeud de départ devrai être passé en argument lors du lancement de l'application.

	Ensuite un message de la console invitera l'utilisateur à saisir le message qu'il souhaite envoyer à ce noeud et ses fils.

	L'arborescence est la suivante au début du développement.
				1
			   / \
			  2   5
			 / \  |
			3   4 6

	Suite à la question 5 l'arborescence est la suivante : 
				1
			   / \
			  2   5
			 / \  |
			3   4_6

II) Le programme


		Pour executer le programme il faut utiliser la commande suivante : java -jar Master.jar <noeud sur lequel vous voulez commencer>(le paramètre n'est pas obligatoire)

	Une fois lancé l'application affiche le message suivant : "Bienvenue sur l'application Akka." 

	Suivi, si un argument a été saisie au lancement du message rappelant ce choix : "Le noeud de départ est le noeud suivant : <le noeud saisie>"

	L'application ensuite invitera l'utilisateur, avant chaque envoie de message, à saisir ce dernier avec le message suivant : "Veuillez saisir le message que vous souhaitez envoyer aux différents noeuds :""

	Un message non considéré par un noeud sera indiqué par le message suivant : "startmessage reçu par le noeud <le nom du noeud>"

	Alors qu'un message interprété par un noeud sera indiqué par le message suivant : "Le message : "test" reçu par le noeud <le nom du noeud>"

III) Architecture 

	1) Les classes 

		Master : La classe principale de l'application, elle contient le main. 
			C'est cette dernière qui va tourner en boucle en attendant les saisies de l'utilisateur.

		Node : La classe représentant un noeud de notre arbre.
			Elle possède des enfants (qui peuvent être considéré comme ces voisins lorsqu'on parle de graphe). Lorsqu'un noeud reçoit un message il le rediffuse à ses enfants si il ne l'a pas déjà reçu avant.
		
		Msg : La classe représentant un message qui doit être traité.
			Un message contient son Id et le message en lui même. L'ID a pour but d'être utilisé par le node
	
		StartMsg : La classe représentant un message ne devant pas être traité.
			Un message ne devant pas être traité contient son Id, le message en lui même et aussi le noeud à partir duquel il doit être diffusé.

	2 ) Gestion des erreurs

		L'ensemble des exceptions déclenchées sont affichées par sur la console après avoir été catch.
		
	3) Tests

		Nous n'avons pas réussi à faire de test : nous avons bloqué sur la façon d'envoyé des messages à nos noeuds sans tout recréer.
