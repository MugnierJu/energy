# Procédure d'installation 

1) Pour faire fonctionner notre projet, et notamment le front, il est nécessaire de télécharger wampserver :
http://www.wampserver.com/

2) Ensuite il faut copier le dossier energyFront de notre projet dans le dossier suivant : _C:\wamp64\www_

3) Après avoir lancé le serveur local de wamp, notre carte est visible à l'adresse suivante http://localhost/energyFront

# Explicaiton du fonctionnement

Notre projet marche de la manière suivante :
- Tout d'abord il faut choisir quelle heuristique on va utiliser. Ce choix se fait dans le App.Java en décommentant les lignes de l'heuristique choisit.

- Après avoir lancé le App.java, un fichier result.json a été crée dans le dossier C:\wamp64\www\energyFront

- Enfin il faut recharger la page qui affiche les résultats

# TP - Livraison avec des véhicules électriques

Réponses 

# Système d'information energie

Seule la première heuristique est implémentée comme suit :

Pour un tableau de N demande 
Pour un vehicule de capacité max C
R le résultat final


On trie par ordre croissant la liste des demandes dans un tableau tab
On part de la demande la plus élevée (tab[n-1])
On ajoute à cette demande la quantité de la demande tab[n-2] dans un variable V = R+n-2
Si tab[n-1] + tab[n-2] > C on ne retient pas n-2 et on itère pour les n-x suivants. Sinon V = R et on passe à n-3
On s'arrête quand la somme est égale à C ou que l’on a parcouru tout le tableau



Les données fournies son formattées et sont présentes sous energy/delivery/resources/

Aucun affichage n'est disponible pour le moment. Néanmoins, il est possible d'accéder à toutes les données en sortie depuis le main (App.java).

