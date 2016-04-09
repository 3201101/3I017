
# Ln - Présentation détaillée de l'application

Log à Rythmes est un projet d'application web de type micro-blogging, écrite en Java. Il s'agit d'un web servlet Java fonctionnant avec un serveur Apache TOMCAT, et communiquant avec des bases de données MySQL et MongoDB.


## Routeur

Le point d'entrée de l'application est le fichier **web.xml**, situé dans le dossier _WebContent/WEB-INF_. Il contient, au format XML, la liste de tous les servlets composant l'application, et les routes - les adresses URL demandées par le client - correspondantes. Quand un client demande une ressource à l'application, que ce soit une page web ou une requête à l'API, le serveur TOMCAT cherche une correspondance pour l'URL demandée dans ce fichier, et charge le servlet correspondant.


## Controlleurs

Les servlets sont situés dans le packet **ln.app**. Chacune de ces classes, dérivant de _HttpServlet_ et implémentant _Servlet_, implémente des méthodes qui seront appelées en fonction de la méthode HTTP utilisée par le client. Par exemple, la réponse d'une requête HTTP ```GET http://app_path/api``` sera donnée par la méthode **ln.app.API.doGet()**.

### Page d'accueil

Le servlet Index sert à afficher la page principale de l'application. Elle effectue essentiellement deux actions.

    req.setAttribute("app", new AppSettings());
    req.setAttribute("page", new PageSettings("", "Accueil", "Accueil"));

Ces lignes ajoutent des attributs qui seront accessibles depuis l'affichage (voir [Vues](#vues)) comme l'adresse absolue du serveur (nécessaire pour fixer le comportement de TOMCAT) ou le nom de la page affichée (utilisée pour générer certaines parties du code automatiquement).

    getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);

Cette ligne va afficher chez le client le rendu du fichier **WebContent/WEB-INF/index.jsp**, les fichiers JSP étant un moyen utilisés par Java pour générer des vues.

## Vues

Tout ce qui concerne l'affichage des pages se trouvera dans le dossier _WebContent_. On y différentie les dossiers _WEB-INF_ (et _META-INF_), qui contienent des fichiers utilisés cþté serveur et non accessibles au client, et les autres fichiers et dossiers qui seront accessibles ouvertement par le client, comme des feuilles de style CSS ou des bibliothèques JavaScript.


### Rendu des templates

Les vues sont générés principalement par les fichiers JSP situés dans le dossier _WebContent/WEB-INF_ (et ses sous-dossiers). Il s'agit de fichiers HTML classiques, mais dans lesquels ont peut ajouter, à la manière de PHP dans ses balises ```<%php %>```, du code Java qui sera exécuté par Java à la compilation ou à l'utilisation des fichiers.

Ce code peut se manifester de plusieurs manières :

* Des balises _jsp_ permettent d'utiliser certaines fonctions, comme l'inclusion d'autres fichiers JSP dans un fichier parent pour construire une page de manière modulaire : ```<jsp:include page="inc/navbar.jsp"/>```. Ils sont notamment utilisés pour factoriser le code des parties réutilisés sur plsuieurs pages, pour éviter la duplication de code.
* Des balises _c_ ajoutent des fonctionnalités telles que des boucles ou des blocs conditionnels. Elles ne sont pas utilisées dans ce projet.
* L'Expression Language fournit des fonctions logiques plus simples que les balises _c_, avec une syntaxe moins verbeuse : ```${ app.name }``` affichera la valeur de l'attribut _app.name_.

### Styles

Pour ce projet, un framework front-end prêt à l'emploi a été utilisé : **Twitter Bootstrap**. Pour cela, on inclut dans chaque page affichée les suivants :

* **css/bootstrap.min.css** contient tous les styles définis par Bootstrap. Il s'agit là d'une version minifié du fichier.
* **js/jquery.js** est une bibliothèque Javascript requise pour certaines fonctionnalités de Bootstrap, et qui est aussi utilisée par ailleurs dans l'application. On utilise ici la version 2.2 de jQuery.
* **bootstrap.min.js** ajoute quelques fonctionnalités améliorant le rendu esthétique des éléments stylisés par Bootstrap.

A celà s'ajoute le fichier **css/style.css** qui contient les styles spécifiques à l'application, dont notamment des surcharges de styles spécifiés par Bootstrap. On y trouvera par exemple ces déclarations :

    .panel-heading > a
    {
        color: #fff;
    }

Cette déclaration modifie la valeur du texte des liens (balsie a) enfants de toute balise de classe HTML _panel-heading_.

    body
    {
        background-color: #70DADA;
        background: url(wallpaper.jpg) no-repeat center fixed; 
        -webkit-background-size: cover; /* pour Chrome et Safari */
        -moz-background-size: cover; /* pour Firefox */
        -o-background-size: cover; /* pour Opera */ 
        background-size: cover;
    }

Ce bloc indique qu'un fond d'écran, **css/wallpaper.jpg** (les URL sont relatives à l'emplacement du fichier CSS), sera appliqué, centré et fixé (par rapport au défilement de la page). La valeur _cover_ de la propriété CSS3 _background-size_ permet d'un comportement intéressant qui permet d'afficher l'image en plein écran quelle que soit la taille du navigateur, et sans déformer l'image. Enfin, les propriétés commençant par un _-_ sont des fixes propriétaires qui servent pour certaines ancienens versions des navigateurs.


### Animations esthétiques

En plus des feuilles de style, on trouve des scripts JavaScript pour améliorer encore un peu le rendu. Voici par exemple un script lié à l'affichage du formulaire d'écriture d'un nouveau message, écrit dans le fichier **WEB-INF/inc/new.jsp** :

    <script type="type/javascript">
        $('#new_options').on('show.bs.collapse', function () {
            $('#new_options_button').text("Moins d'options");
        });
        $('#new_options').on('hide.bs.collapse', function () {
            $('#new_options_button').text("Plus d'options");
        });
    </script>

Ce code utilise des fonctiallités apportées par jQuery (dont la bibliothèque est incluse plus haut dans la page). Il sélectionne, à deux reprises, l'objet DOM dans la page HTML qui possède l'identifiant unique _new\_options_. Gr«ce à la méthode ```on()```, elle crée deux fonctions anonymes qui seront appelés chaque fois que cet objet émettera les événements _show.bs.collapse_ et _hide.bs.collapse_. Ces événements sont créés par Bootstrap et correspondent respectivement à l'apparition et la disparition du bloc _new\_options_ suite aux fonctions _collapse_ de Bootstrap.


## Données

Mis à part les données statiques comme le texte des boutons et des éléments esthétiques apportées par les styles, le site web doit surtout afficher du contenu. Ces données, dans le cadre de ce projet, sont stockés dans deux bases de données : une de type MySQL, qui stockera les donénes des utilisateurs notemment, et une base MongoDB, plus adaptée à une éventuelle grande quantiée de données à traiter, pour les messages postés.

Ces données sont accessibles par le site web cþté client à l'aide de fonctions JavaScript, ou directement par le client, au moyen d'une API respectant partiellement les spécifications REST. Elle permet d'obtenir, à l'aide de requêtes HTTP, des données qui seront retournées au client au format JSON.

Cette API interagit avec les bases de données à travers une bibliothèque minimaliste codée _from scratch_, qui ajoute une couche d'abstraction à la communication avec les bases de données.

### Rendu API

Le point d'entrée elle-même est une _Servlet_, implémentée dans la classe **ln.app.API**.

Une méthode ```index()``` permet d'isoler l'affichage de la page web qui présentera l'API au client.

Lorsqu'une requête HTTP est envoyée à l'API, par exemple ```GET http://app_path/api/method```, elle est reçue par la méthode correspondage du Servlet, ```doGet()``` ici (voir [Controlleurs](#controlleurs)). Chacune de ces méthodes va ici analyser l'URL demandée, comme une sous-table de routage. Lorsqu'une URL connue est trouvée, on renvoie au client le résultat de la méthode correspondante de l'API, un message d'erreur généré par ```AbstractService.serviceRefused()``` sinon.

### Logique API

L'API elle-même est implémenté dans plusieurs classes du paquet **ln.api**. Chacune de ces classes déclare plusieurs méthodes, une par requête possible à l'API, et chacune retourne  un résultat au format JSON. Par exemple :

    public static JSONObject get() throws SQLException, JSONException
    {
        ResultSet r = DBService.select("users");
        JSONObject o = new JSONObject();
        JSONArray a = new JSONArray();
        while(r.next())
        {
            JSONObject j = new JSONObject();
            j.put("username", r.getString("login"));
            /* ... */
            a.put(j);
        }
        o.put("users", a);
        return o;
    }

Cette méthode répond à la requ¶te ```GET http://app_path/api/users```. Elle effectue une requête à la base de données à l'aide d'une méthode de la classe **ln.db.DBService**, puis construit un objet _JSONObject_ contenant les réponses.

### Communication API

La dernière couche de l'API est la classe **ln.db.DBService** qui contient les méthodes d'accès aux bases de données. Elle implémente par exemple une méthode généralse pour récupérer des données d'une base MySQL sans avoir à construire systématiquement la requête SQL, ce qui peut être relativement laborieux.

    public static ResultSet select(String table, Collection<String> select_name, Collection<String> where_name, Collection<String> where_val, String opt) throws SQLException

Cette classe a besoin de données d'idenfication permettant d'accéder aux bonnes bases de données. Celles-ci sont stockés dans une autre classe, **ln.db.DBStatic**, ce qui permet d'échanger plusieurs configuration, et de ne pas versionenr ces informations sur un dépôt.

    public final class DBSettings
    {
        public static final String db_host = /* Adresse du serveur */;
        public static final String db_name = /* Nom de la base */;
        public static final String db_user = /* Utilisateur */;
        public static final String db_pass = /* Mot de passe */;
        public static final int db_mongo_port = /* Port du serveur Mongo */;
        public static final int db_mysql_port = /* Port du serveur MySQL */;
        public static final boolean db_mysql_pooling = /* Option pour le matient des connexions par Java */;
    }

> Cette partie peut largement être améliorée. On peut augmenter l'abstraction en fusionnant les méthodes liés à MySQL et MongoDB (en gardant les structures utilisés avec MongoDB, plus intéressantes et pratiques). Et cela restera du bas de gamme par rapport à un framework complet d'interaction avec les bases de données.
