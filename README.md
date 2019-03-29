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
