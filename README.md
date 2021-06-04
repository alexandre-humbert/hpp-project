# Coronavirus HPP project
More information : https://github.com/telecom-se/hpp/tree/2020-2021/project
### Members :
* Alexandre Humbert
* Aurélien Arbaretaz

### Unit tests :
* Test du Reader :\
  On compare les données générée par le Reader avec le résultat stocké dans un fichier texte.
  L'objectif est de s'assurer que le programme récupère les cas l'ordre à partir des différents fichiers.
  
* Test de l'exemple :\
  Ce test exécute l'exemple du projet et vérifie que le résultat obtenu est correct.
  
* Test du cas où des scores sont identiques :\
  On véfifie que dans le cas où deux scores sont identiques, c'est la plus vieille chaine qui prime.\
  Donnée en entree :
  
```
0, "Archibald", "Haddock", "22/03/1963", 1584358000, "unknown"
1, "Tryphon", "Tournesol", "07/11/1952", 1584724800, "unknown"
2, "Seraphin", "Lampion", "12/05/1969", 1584912000, "unknown"
3, "Bianca", "Castafiore", "18/04/1965", 1585058000, 0
4, "Roberto", "Rastapopoulos", "29/12/1962", 1585145000, 1
5, "Aristide", "Filoselle", "10/05/1954", 1585255000, "unknown"
6, "Ivanovitch", "Sakharine", "12/03/1962", 1585375000, "unknown"
```
Sortie :\
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
Haddock puis Tournesol puis Lampion sont contaminés par des personnes inconnues et deviennent root_id. Castafiore est contaminée par Haddock ce qui ajoute 10 au score de la première chaine et la contamination d'Haddock ne compte plus que pour 4 points donc un total de 14. Rastapopoulos est contaminé par Tournesol, ils sont tous les deux à 10 donc leur chaine devient la première. Filoselle est contaminé par une personne inconnue est devient root_id avec un score de 10. Il n'apparait pas dans le TOP3 car son score est identique à la chaine root_id=2 plus vieille. Enfin, Sakharine (5) se retrouve dans la même situation. La chaine root_id=1 passe à 14 points et se retouve à égalité avec la chaine root_id=0 qui repasse première car plus vieille.

* Test du cas où une personne est infectée par une personne guérie :\
  
