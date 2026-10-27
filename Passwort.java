import java.security.SecureRandom;

public class Passwort
{
    private static final String GROSSBUCHSTABEN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String KLEINBUCHSTABEN = "abcdefghijklmnopqrstuvwxyz";
    private static final String ZAHLEN = "0123456789";
    private static final String SONDERZEICHEN = "!?#$%&*+-_.";

    private int laenge;
    private boolean mitSonderzeichen;
    private SecureRandom zufall;

    public Passwort()
    {
        this(8, false);
    }

    public Passwort(int laenge, boolean mitSonderzeichen)
    {
        setLaenge(laenge);
        this.mitSonderzeichen = mitSonderzeichen;
        zufall = new SecureRandom();
    }

    public void setLaenge(int laenge)
    {
        if (laenge < 4)
        {
            laenge = 4;
        }
        this.laenge = laenge;
    }

    public void setMitSonderzeichen(boolean mitSonderzeichen)
    {
        this.mitSonderzeichen = mitSonderzeichen;
    }

    public String erzeugePasswort()
    {
        String alleZeichen = GROSSBUCHSTABEN + KLEINBUCHSTABEN + ZAHLEN;
        if (mitSonderzeichen)
        {
            alleZeichen = alleZeichen + SONDERZEICHEN;
        }

        char[] passwort = new char[laenge];

        // Je ein Zeichen jeder Art garantieren
        passwort[0] = zufallszeichen(GROSSBUCHSTABEN);
        passwort[1] = zufallszeichen(KLEINBUCHSTABEN);
        passwort[2] = zufallszeichen(ZAHLEN);

        int start = 3;
        if (mitSonderzeichen)
        {
            passwort[3] = zufallszeichen(SONDERZEICHEN);
            start = 4;
        }

        for (int i = start; i < laenge; i++)
        {
            passwort[i] = zufallszeichen(alleZeichen);
        }

        for (int i = laenge - 1; i > 0; i--)
        {
            int j = zufall.nextInt(i + 1);
            char temp = passwort[i];
            passwort[i] = passwort[j];
            passwort[j] = temp;
        }

        return new String(passwort);
    }

    public String erzeugeMehrere(int anzahl)
    {
        String ergebnis = "";
        for (int i = 0; i < anzahl; i++)
        {
            ergebnis = ergebnis + erzeugePasswort() + "\n";
        }
        return ergebnis;
    }

    public String bewerteStaerke(String passwort)
    {
        int punkte = 0;

        if (passwort.length() >= 8)  { punkte++; }
        if (passwort.length() >= 12) { punkte++; }
        if (enthaeltZeichenAus(passwort, GROSSBUCHSTABEN))  { punkte++; }
        if (enthaeltZeichenAus(passwort, KLEINBUCHSTABEN))  { punkte++; }
        if (enthaeltZeichenAus(passwort, ZAHLEN))           { punkte++; }
        if (enthaeltZeichenAus(passwort, SONDERZEICHEN))    { punkte++; }

        if (punkte <= 3)
        {
            return "schwach";
        }
        else if (punkte <= 5)
        {
            return "mittel";
        }
        else
        {
            return "stark";
        }
    }

    private boolean enthaeltZeichenAus(String text, String zeichenmenge)
    {
        for (int i = 0; i < text.length(); i++)
        {
            if (zeichenmenge.indexOf(text.charAt(i)) >= 0)
            {
                return true;
            }
        }
        return false;
    }

    private char zufallszeichen(String zeichen)
    {
        return zeichen.charAt(zufall.nextInt(zeichen.length()));
    }
}