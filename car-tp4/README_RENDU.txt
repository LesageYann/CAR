LESAGE Yann & PETIT Antoine

TP ^4 : Java EE

I) Introduction


		L'objectif du TP était d'implanter une application en Java EE, pour ce faire on a mis en place un site d'achat de livres.


II) Le programme


		Pour executer le programme il faut suivre les instructions du README fourni avec l'application
		
		Le site est composé de différentes pages : 	- Une page d'accueil
													- Un formulaire permettant la création de nouveau livre
													- Une page récapitulant tout les auteurs présents sur le site
													- Une page contenant la bilbliothèque des livres présents en base
													- Une page récapitulant le contenu du panier

		Sur la page de la bibliothèque il est possible de voir combien de livre il y a dans notre panier au total et on peut ajouter de nouveaux livres au panier.

		Sur la page du panier on peut voir combien de livre sont contenus dans ce dernier ainsi que pour chaque livre dans le panier la quantité que l'on a ajouté de ce type. On peut retirer des livres à partir de cet écran.

		Nous n'avons pas réussi à mettre en place la persistance sur l'application, l'entity manager persiste à être null empéchant toute intéraction avec ce dernier.

III) Architecture 

	1) Les classes 

		BookDAO : La classe représentant les livres dans l'application.
			EntityBean composé d'un id, auteur, titre et d'une année.

		AddPanierServlet : Servlet permettant l'ajout d'un livre dans le panier.

		AjoutServlet : Servlet permettant l'ajout d'un nouveau livre en base.

		BookLibItf : Interface des services proposé par le SessionBean BookLib
			Les services proposés sont les suivants : 	- initialisation de tout les éléments nécessaire à l'application
														- obtention des auteurs des livres présent sur l'application
														- obtention de tout les livres présent sur l'application
														- ajout d'un livre 
														- obtention d'un livre par son id

		BookLib : Implémentation de l'interface décrite précédement

		BookService : Tentative de création d'un BeanSession utilisant l'entity manager

		InitialisationServlet : Servlet initialisant l'application

		Panier : La classe représentant un panier
			Composé d'un id et d'une map reliant les livres aux quantités possédées.

		RemovePanierServlet : Servlet permettant la suppression d'un livre du panier.

	2 ) Gestion des erreurs

		L'ensemble des exceptions déclenchées sont affichées par sur la console après avoir été catch.
		Les problèmes lors de l'initialisation sont quant à eux remonté sur la page résultante.
		
	3) Tests

		Nous n'avons pas réussi à faire de test.
