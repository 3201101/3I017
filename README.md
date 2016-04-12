
# Ln - Log à Rythmes

                  _____            _____          
                 /\    \          /\    \         
                /::\____\        /::\____\        
               /:::/    /       /::::|   |        
              /:::/    /       /:::::|   |          
             /:::/    /       /::::::|   |        
            /:::/    /       /:::/|::|   |        
           /:::/    /       /:::/ |::|   |                  Log à Rythmes
          /:::/    /       /:::/  |::|   | _____            
         /:::/    /       /:::/   |::|   |/\    \           Benjamin Loglisci
        /:::/____/       /:: /    |::|   /::\____\          André Nasturas
        \:::\    \       \::/    /|::|  /:::/    /          
         \:::\    \       \/____/ |::| /:::/    /           2016
          \:::\    \              |::|/:::/    /            
           \:::\    \             |::::::/    /   
            \:::\    \            |:::::/    /    
             \:::\    \           |::::/    /     
              \:::\    \          /:::/    /      
               \:::\____\        /:::/    /       
                \::/    /        \::/    /        
                 \/____/          \/____/       
    
    

Log à Rythmes est un projet d'application web de type micro-blogging, développé en 2016 par Benjamin Loglisci et André Nasturas, dans le cadre du cours [3i017 Technologies du Web](http://www-licence.ufr-info-p6.jussieu.fr/lmd/licence//public/espace_public/offres_formation/descr_ue.php?code_ue=3I017) de l'UPMC.

## Installation

Cette application a été développé en Java, et requiert un serveur TOMCAT, une base de données MySQL et une base de données MongoDB.

1. Clonez le dépôt : ```git clone git://github.com/3201101/3I017.git```.
2. Renommez le fichier **ln.db.DBSettings.java.sample** en **ln.db.DBSettings.java**.
3. Complétez le fichier **ln.db.DBSettings.java.sample** dans le dossier _src/ln_ avec les identifiants de vos bases de données.
4. Modifiez éventuellement les fichiers **ln.app.AppSettings** et **style.css** respectivement dans les dossiers _src/ln_ et _WebContent/css_ à votre convenance.
5. Exportez le projet au format WAR.
6. Mettez en ligne le fichier WAR sur TOMCAT.
7. Enjoy !


## Présentation

Une présentation détaillée de l'application, faisant aussi office de rapport de projet, est disponible [ici](https://github.com/3201101/3I017/blob/master/REPORT.md).


## API

L'application fournit une API permettant d'interagir avec elle. Elle est documentée dans l'application elle-même à l'adresse _/api_.