# Coronavirus HPP project
More information : https://github.com/telecom-se/hpp/tree/2020-2021/project
### Members :
* Alexandre Humbert
* Aurélien Arbaretaz

### Unit tests :
* Test du Reader :\
  On compare les données générée par le Reader avec le résultat stocké dans un fichier texte.
  L'objectif est de s'assurer que le programme récupère les cas dans l'ordre des contaminations selon les timestamps depuis les différents fichiers.
  
* Test de l'exemple :\
  Ce test exécute l'exemple du projet et vérifie que le résultat obtenu est correct.\
  Cet exemple est détaillé sur le git du projet.
  
* Test du cas où des scores sont identiques :\
  On véfifie que dans le cas où deux scores sont identiques, c'est la plus vieille chaine qui prime.\
Entrée :
  
```
0, "Archibald", "Haddock", "22/03/1963", 1584358000, "unknown"
1, "Tryphon", "Tournesol", "07/11/1952", 1584724800, "unknown"
2, "Seraphin", "Lampion", "12/05/1969", 1584912000, "unknown"
3, "Bianca", "Castafiore", "18/04/1965", 1585058000, 0
4, "Roberto", "Rastapopoulos", "29/12/1962", 1585145000, 1
5, "Aristide", "Filoselle", "10/05/1954", 1585255000, "unknown"
6, "Ivanovitch", "Sakharine", "12/03/1962", 1585375000, "unknown"
```
Sortie :
```
[[France, root_id=0, score=10]]
[[France, root_id=0, score=10][France, root_id=1, score=10]]
[[France, root_id=0, score=10][France, root_id=1, score=10][France, root_id=2, score=10]]
[[France, root_id=0, score=14][France, root_id=1, score=10][France, root_id=2, score=10]]
[[France, root_id=1, score=20][France, root_id=0, score=14][France, root_id=2, score=10]]
[[France, root_id=1, score=20][France, root_id=0, score=14][France, root_id=2, score=10]]
[[France, root_id=0, score=14][France, root_id=1, score=14][France, root_id=2, score=10]]
```

Détails :\
Haddock (0) puis Tournesol (1) puis Lampion (2) sont contaminés par des personnes inconnues et deviennent root_id. Castafiore (3) est contaminée par Haddock (0) ce qui ajoute 10 au score de la première chaine et la contamination d'Haddock (0) ne compte plus que pour 4 points (trop vieille) donc la chaine a un total de 14. Rastapopoulos (4) est contaminé par Tournesol (1), ils sont tous les deux à 10 donc leur chaine devient la première avec un score de 20. Filoselle (5) est contaminé par une personne inconnue et devient root_id avec un score de 10. Il n'apparait pas dans le TOP3 car son score est identique à la chaine root_id=2 plus vieille. Enfin, Sakharine (6) se retrouve dans la même situation. La chaine root_id=1 passe à 14 points et se retouve à égalité avec la chaine root_id=0 qui repasse première car plus vieille.

* Test du cas où une personne est infectée par une personne guérie :\
  Ce test vérifie que si une personne est contaminé par une personne infectée depuis plus de 14 jours, alors elle devient root_id.
Entrée :
```
0, "Archibald", "Haddock", "22/03/1963", 1584558000, unknown
1, "Tryphon", "Tournesol", "07/11/1952", 1584624800, 0
2, "Seraphin", "Lampion", "12/05/1969", 1588312000, 0
3, "Bianca", "Castafiore", "18/04/1965", 1588558000, 2
4, "Roberto", "Rastapopoulos", "29/12/1962", 1588645000, 3
```
Sortie :
```
[[France, root_id=0, score=10]]
[[France, root_id=0, score=20]]
[[France, root_id=2, score=10]]
[[France, root_id=2, score=20]]
[[France, root_id=2, score=30]]

```

Détails :\
Haddock est contaminé par une personne inconnue, il devient root_id de la première chaine qui à un score de 10. Puis Tournseol (1) contaminé par Haddock (0) ajoute 1à point à cette chaine. Lampion (3) est infecté par une Haddock (0) mais cette contamination datant de plus d'un mois, il devient root_id et la chaine de Haddock est summprimée. Ensuite Bianca (3) puis Rastapopoulos (4) viennent s'ajouter à cette nouvelle chaine.




  
