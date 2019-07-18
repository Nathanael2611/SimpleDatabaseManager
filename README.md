# SimpleDatabaseManager, qu'est-ce que c'est ?
SimpleDatabaseManager est, avant-tout une lib pour vous les développeurs de mod minecraft !
La sauvegarde de données persistantes n'est pas une tâche facile, si vous débutez, vous savez de quoi je parle !
Si vous créez une variable à laquelle vous assignez une valeur, quand vous relancerez votre jeu, elle aura été reinitialisée.
Et bien justement SimpleDatabaseManager contre ce souci en proposant un système de gestion de data dans les joueurs (chaque joueur à une base de donnée assignée) ainsi qu'un système de base de données auxquelles vous pouvez accéder avec leur nom!
Dans ces "base de données" vous pouvez stocker des integers, des doubles, des floats, des string, ainsi que des boolan ! Et bien entendu ces données sont persistantes, SimpleDatabaseManager s'occupe de tout !
En plus d'ajouter un système de base de données persistantes, SimpleDatabaseManager ne s'arrête pas là, il permet aussi de synchroniser le data d'un joueur, avec celui-ci. 
Dès lors le client pourra avoir l'accès en lecture à toutes les données présentes dans la base de données lui étant assignée. 
Vous pouvez aussi choisir de partager certaines base de donnée (en lecture seulement) avec des EntityPlayer de votre choix.
En résumé, SimpleDatabaseManager est une lib permettant de stocker facilement des données persistantes, mais aussi de les synchroniser entre le server et le client.

# Mais ? Comment ça marche ?
Premièrement, télécharger la dernière release de SimpleDatabaseManager et ajoutez-la en dépendance de votre mod. (que ce soit avec gradle ou depuis votre IDE directement)
Et c'est tout ! Désormais, passons au code:

## Stocker du data dans une base de donnée
Premièrement, vous allez devoir get la base de donnée via son nom (la création se fait automatiquement si vous gettez une db inexistante), pour ce faire:
```JAVA
Database db = Databases.getDatabase("votre_database");
```
Nous avons donc créer une instance de notre base de donnée nommée "db".
