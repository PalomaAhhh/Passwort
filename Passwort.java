import java.util.Random;

public class Passwort
{
    private static final String GROSSBUCHSTABEN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String KLEINBUCHSTABEN = "abcdefghijklmnopqrstuvwxyz";
    private static final String ZAHLEN = "0123456789";
    private static final String ALLE_ZEICHEN = GROSSBUCHSTABEN + KLEINBUCHSTABEN + ZAHLEN;

    private int laenge;
    private Random zufall;

    public Passwort()
    {
        laenge = 8;
        zufall = new Random();
    }

    public String erzeugePasswort()
    {
        char[] passwort = new char[laenge];

        // Je ein Zeichen jeder Art garantieren
        passwort[0] = zufallszeichen(GROSSBUCHSTABEN);
        passwort[1] = zufallszeichen(KLEINBUCHSTABEN);
        passwort[2] = zufallszeichen(ZAHLEN);

        // Restliche Zeichen aus allen erlaubten Zeichen
        for (int i = 3; i < laenge; i++)
        {
            passwort[i] = zufallszeichen(ALLE_ZEICHEN);
        }

        // Reihenfolge mischen, damit die ersten drei nicht immer gleich aufgebaut sind
        for (int i = laenge - 1; i > 0; i--)
        {
            int j = zufall.nextInt(i + 1);
            char temp = passwort[i];
            passwort[i] = passwort[j];
            passwort[j] = temp;
        }

        return new String(passwort);
    }

    private char zufallszeichen(String zeichen)
    {
        int index = zufall.nextInt(zeichen.length());
        return zeichen.charAt(index);
    }
}