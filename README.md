# SimpleDatabaseManager, qu'est-ce que c'est ?

<details>
  <summary>French description</summary>
SimpleDatabaseManager est, avant-tout une lib pour vous les développeurs de mod minecraft !
La sauvegarde de données persistantes n'est pas une tâche facile, si vous débutez, vous savez de quoi je parle !
Si vous créez une variable à laquelle vous assignez une valeur, quand vous relancerez votre jeu, elle aura été reinitialisée.
Et bien justement SimpleDatabaseManager contre ce souci en proposant un système de gestion de data dans les joueurs (chaque joueur à une base de donnée assignée) ainsi qu'un système de base de données auxquelles vous pouvez accéder avec leur nom!
Dans ces "base de données" vous pouvez stocker des integers, des doubles, des floats, des string, ainsi que des boolan ! Et bien entendu ces données sont persistantes, SimpleDatabaseManager s'occupe de tout !
En plus d'ajouter un système de base de données persistantes, SimpleDatabaseManager ne s'arrête pas là, il permet aussi de synchroniser le data d'un joueur, avec celui-ci. 
Dès lors le client pourra avoir l'accès en lecture à toutes les données présentes dans la base de données lui étant assignée. 
Vous pouvez aussi choisir de partager certaines base de donnée (en lecture seulement) avec des EntityPlayer de votre choix.
En résumé, SimpleDatabaseManager est une lib permettant de stocker facilement des données persistantes, mais aussi de les synchroniser entre le server et le client.
</details>
<details>
  <summary>English description</summary>
  SimpleDatabaseManager is, above all, a lib for you minecraft mod developers!
Backing up persistent data is not an easy task, if you are a beginner, you know what I mean!
If you create a variable to which you assign a value, when you restart your game, it will have been reset.
Well, SimpleDatabaseManager against this problem by offering a data management system in the players (each player has an assigned database) as well as a database system that you can access with their name!
In these "databases" you can store integers, doubles, floats, thongs, as well as boolans! And of course this data is persistent, SimpleDatabaseManager takes care of everything!
In addition to adding a persistent database system, SimpleDatabaseManager does not stop there, it also allows you to synchronize a player's data with it. 
From then on, the customer will be able to have read access to all the data present in the database assigned to him. 
You can also choose to share some databases (read only) with EntityPlayers of your choice.
In short, SimpleDatabaseManager is a lib that allows you to easily store persistent data, but also to synchronize them between the server and the client.
</details>


# Mais ? Comment ça marche ?
Premièrement, télécharger la dernière release de SimpleDatabaseManager et ajoutez-la en dépendance de votre mod. (que ce soit avec gradle ou depuis votre IDE directement)
Et c'est tout ! Désormais, passons au code:

## Stocker du data dans une base de donnée
### Récupérer une base de donnée via son nom
Premièrement, vous allez devoir get la base de donnée via son nom (la création se fait automatiquement si vous gettez une db inexistante), pour ce faire:
```JAVA
Database db = Databases.getDatabase("votre_database");
```
Nous avons donc créer une instance de notre base de donnée nommée "db".
### Stocker des valeurs
C'est bien beau d'avoir une base de donnée, mais elle ne contient pour le moment rien du tout !
Pour lui assigner des valeurs vous pouvez vous y prendre ainsi:
```JAVA
Database db = Databases.getDatabase("votre_database");
db.setString("stringtest", "mon string de test");
db.setInteger("integertest", 3);
db.setDouble("doubletest", 2.0);
db.setFloat("floattest", 47.9f);
db.setBoolean("booleantest", true);
```
Simple ? Non ?
En fait, c'est un peu comme les HashMap, ça fonctionne avec un système de valeur, assignée à une clé.
Toutes les méthodes de set contienne en premier argument la clé, et en deuxième argument la valeur ! 
### Lire des valeurs
Bon, maintenant qu'on sait stocker des valeurs, il faut bien savoir les récupérer... Sinon le système serait inutile.
Et bien c'est tout simple:
```JAVA
Database db = Databases.getDatabase("votre_database");
String str = db.getString("stringtest"); // str sera égal à "mon string de test"
int entier = db.getInteger("integertest"); // entier sera égal à 3
double d = db.getDouble("doubletest"); // d sera égal à 2.0
float f = db.getFloat("floattest"); // f sera égal à 47.9f
boolean b = db.getBoolean("booleantest"); // b sera égal à true
```
Bien entendu, le nom des variables dans lesquelles vous lisez les valeurs n'a aucune importance (je préfère le préciser, car avec certains :rolling_eyes:
### Stocker des valeurs dans un joueur
Comme je vous l'ai dit, chaque joueur possède une database qui lui est dédiée, vous pouvez donc get cette fameuse database comme ceci:
```JAVA
EntityPlayer player = /* Votre instance d'EntityPlayer */;
Database playerdata = Databases.getPlayerData(player);
```
Après comme c'est une database comme les autres, vous vous en servez, bah, comme les autres. x)
## Synchronisation des base de données
Comme dit dans le paragraphe de présentation, SimpleDatabaseManager gère aussi la synchronisation entre le client et le serveur !
Voici comment l'utiliser;
### Synchronisation des playerdatas
La synchronisation des playerdata est gérée **automatiquement** le clien à accès en lecture au playerdata du joueur lui étant assigné.
Pour accéder **DEPUIS LE CLIENT** à votre playerdata, c'est comme ça:
```JAVA
DatabaseReadOnly playerData = ClientDatabases.getPersonalPlayerData();
```
:warning: Attention, n'essayez pas d'utiliser les méthodes de set de valeurs dans une instance de DatabaseReadOnly, les bases de données client ne sont accessible qu'en lecture. Vous ne pouvez pas écrire dedans ! :warning:
