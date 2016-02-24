package net.ln.db;

/**
 * DBSettings
 *
 * Cet objet contient les paramètres d'environnement de l'application, tels que les identifiants et mots de passe des bases de données utilisées.
 * Ce fichier ne devrait pas être accessible publiquement, ou laissé dans un code source ouvert, pour des raisons évidentes de sécurité.
 */
public interface DBSettings
{
    static final String db_host = "132.227.201.129";
    static final String db_name = "gr2_loglisci_nas";
    static final String db_user = "gr2_loglisci_nas";
    static final String db_pass = "ZDV328"
    static final int db_mysql_port = 33306;
    static final int db_mongo_port = 27130;
    static final boolean db_mysql_pooling = false;
}
