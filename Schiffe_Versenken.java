/**
 *
 * Beschreibung
 *
 * @version 0.1 vom 17.09.2020
 * @Steinberg 
 */
import java.util.*;

public class Schiffe_Versenken {
  
  public static char[][][][] spielfeld = new char[2][2][11][11];       //[0=entdckte Schiffe;1=alleSchiffe][0=Spieler0;1=Spieler1][FeldVertikal(oben-unten)][FeldHorizontal(links-rechts)]    "-" = Schiff rechts-links; "~" getroffenes Schiff rechts-links;  "|" Schiff oben-unten;   "/" getroffenes Schiff oben-unten;  "0" Wasser
  public static boolean spieler = false;                               //false=Spieler0;true=Spieler1
  public static Scanner sc = new Scanner(System.in);
  
  public static void main(String[] args) {
    logic();
  } // end of main
  
  public static void logicStart() {
    init();
    plaziereSchiffe();
    spieler=!spieler;
    plaziereSchiffe();
    spieler=!spieler;
  }
  
  public static void logic() {
    logicStart();
    while (!hasWon(!spieler)) {
      System.out.println("Es ist Spieler " + spielerAsByte(spieler) + " an der Reihe!");
      schuss();
    } // end of while
    System.out.println("Spieler " + spielerAsByte(!spieler) + " hat gewonnen! Herzlichen Glückwunsch!");
  }
      
  public static void cleanTerminal() {
    for (int i = 0; i < 30; i++) {
      System.out.println();
    }
  }
      
  public static boolean hasWon(boolean zuTestenderSpieler) {
    for (int i = 0; i < spielfeld[0][0].length; i++) {
      for (int j = 0; j < spielfeld[0][0][0].length; j++) {
        if (spielfeld[1][spielerAsByte(!zuTestenderSpieler)][i][j] == '|' || spielfeld[1][spielerAsByte(!zuTestenderSpieler)][i][j] == '-') {
          return false;
        } // end of if
      }
    }
    return true;
  }
      
  public static void plaziereSchiffe() {
    System.out.println("Es ist Spieler " + spielerAsByte(spieler) + " an der Reihe!");
    plaziereSchiff(5);
    plaziereSchiff(4);
    plaziereSchiff(4);
    plaziereSchiff(3);
    plaziereSchiff(3);
    plaziereSchiff(3);
    plaziereSchiff(2);
    plaziereSchiff(2);
    plaziereSchiff(2);
    plaziereSchiff(2);
    cleanTerminal();
  }
      
  public static void schuss(){
    boolean repeat = true;
    int feldH = 15;
    int feldV = 15;
    
    
    do {
      printSpielfeld(0,spielerAsByte(spieler));
      System.out.print("Bitte gebe das Feld das du abschießen m\u00F6chtest ein:\n> ");
      String input = sc.next();
      if (input.charAt(0)>64 && input.charAt(0)<75 || input.charAt(0)>96 && input.charAt(0)<107) {
        if (input.charAt(0)>64 && input.charAt(0)<75){
          feldH = (input.charAt(0)-65);
        } else {
          feldH = (input.charAt(0)-97);
        } // end of if-else
        if (input.charAt(1)>47 && input.charAt(1)<58){
          feldV = (input.charAt(1)-48);
          
          switch (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH]) {
            case('0') :
            case('/') :
            case('~') :
              System.out.println("Dies war leider ein Schuss ins Wasser. Nun ist der n\u00E4chste Spieler an der Reihe!");
              spielfeld[0][spielerAsByte(spieler)][feldV][feldH] = '0';
              repeat = false;
              spieler = !spieler;
              break;
            case('|') :
            case('-') :
              switch (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH]) {
                case('|') :
                  boolean fullShipV = true;  //ist das Schiff vollständig zerstört?
                  boolean continueForV = true;
                  spielfeld[1][spielerAsByte(!spieler)][feldV][feldH] = '/';
                  spielfeld[0][spielerAsByte(spieler)][feldV][feldH] = '/';
                  try {
                    for (int i = 0; continueForV; i++) {
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV+i][feldH] != '0') {
                        if (spielfeld[1][spielerAsByte(!spieler)][feldV+i][feldH] == '|') {
                          fullShipV = false;
                        } // end of if
                      } // end of if
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV+i][feldH] == '0') {
                        continueForV = false;
                      } // end of if  
                    }
                  }
                  catch (ArrayIndexOutOfBoundsException e) {
                    
                  }
                  continueForV = true;
                  try {
                    for (int i = 0; continueForV; i++) {
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV-i][feldH] != '0') {
                        if (spielfeld[1][spielerAsByte(!spieler)][feldV-i][feldH] == '|') {
                          fullShipV = false;
                        } // end of if
                      } // end of if
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV-i][feldH] == '0') {
                        continueForV = false;
                      } // end of if  
                    }
                  }
                  catch (ArrayIndexOutOfBoundsException e) {
                    
                  }
                  if (fullShipV) {
                    System.out.println("Treffer versenkt!");
                    
                  } else {
                    System.out.println("Treffer!");
                  } 
                  break;
                case('-') :
                  boolean fullShipH = true;  //ist das Schiff vollständig zerstört?
                  boolean continueForH = true;
                  spielfeld[1][spielerAsByte(!spieler)][feldV][feldH] = '~';
                  spielfeld[0][spielerAsByte(spieler)][feldV][feldH] = '~';
                  try {
                    for (int i = 0; continueForH; i++) {
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH+i] != '0') {
                        if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH+i] == '-') {
                          fullShipH = false;
                        } // end of if
                      } // end of if
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH+i] == '0') {
                        continueForH = false;
                      } // end of if  
                    }
                  }
                  catch (ArrayIndexOutOfBoundsException e) {
                    
                  }
                  continueForH = true;
                  try{
                    for (int i = 0; continueForH; i++) {
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH-i] != '0') {
                        if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH-i] == '-') {
                          fullShipH = false;
                        } // end of if
                      } // end of if
                      if (spielfeld[1][spielerAsByte(!spieler)][feldV][feldH-i] == '0') {
                        continueForH = false;
                      } // end of if  
                    }
                  }
                  catch (ArrayIndexOutOfBoundsException e) {
                    
                  }
                  if (fullShipH) {
                    System.out.println("Treffer versenkt!"); //Das TrefferVersenkt System funktioniert nicht
                    
                  } else {
                    System.out.println("Treffer!");
                  }
                  break;
              }
              
              System.out.println("Du bist nun erneut an der Reihe.");
              
          } // switch
        } else {
          System.out.println("Sie haben leider eine ung\u00FCltige Nummer eingegeben. Bitte widerholen Sie Ihre Eingabe!");
        } // end of if-else
      } else {
        System.out.println("Sie haben leider einen ung\u00FCltigen Buchstaben eingegeben. Bitte widerholen Sie Ihre Eingabe!");
      }// end of if-else
    } while (repeat); // end of do-while{ 
    
  }
      
  public static void printSpielfeld(int type,int spieler){
    System.out.println("   ABCDEFGHIJ\n");
    for (int i = 0; i < 10; i++) {
      System.out.print(i + "  ");
      for (int j = 0; j < 10; j++) {
        if (type == 1) {
          System.out.print(spielfeld[type][spieler][i][j]);
        } // end of if
        if (type == 0) {
          switch (spielfeld[type][spieler][i][j]) {
            case '?' :
            case '0' :
              System.out.print(spielfeld[type][spieler][i][j]);
              break;
            case '~' :
            case '/' :
              System.out.print("X");
              break;
            default:
          
          } // end of switch
        } // end of if
      }
      System.out.print("\n");
    }
  }
      
  public static void init(){
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 11; j++) {
        for (int k = 0; k < 11; k++) {
          spielfeld[0][i][j][k] = '?';
          
        } // end of for
      }
    }
    
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 10; j++) {
        for (int k = 0; k < 10; k++) {
          spielfeld[1][i][j][k] = '0';
          
        } // end of for
      }
    }
    
    
  }
      
  public static void plaziereSchiff(int laengePlus1) {
    int laenge = laengePlus1 - 1;
    int inputH;
    int inputV;
    boolean left = false;
    boolean up = false;
    boolean right = false;
    boolean down = false;
    printSpielfeld(1,spielerAsByte(spieler));
    System.out.print("Bitte gib das Feld ein, in dem das Schiff der länge " + laengePlus1 + " starten soll!\n> ");
    String input = sc.next();
    if (input.charAt(0)>64 && input.charAt(0)<75 || input.charAt(0)>96 && input.charAt(0)<107) {
      if (input.charAt(0)>64 && input.charAt(0)<75){
        inputH = (input.charAt(0)-65);
      } else {
        inputH = (input.charAt(0)-97);
      } // end of if-else
      if (input.charAt(1)>47 && input.charAt(1)<58){
        inputV = (input.charAt(1)-48);
        
        if(inputH - laenge > -1){
          for (int i = 0; i < laenge+1; i++) {
            if(!validateSchiffsort(inputV,inputH-i)) {
              break;
            }
            if (i == laenge) {
              System.out.println("Wenn das Schiff auf dem Feld " + ((char) ((inputH - laenge + 65) & 0xFF)) + ((char) ((inputV + 48) & 0xFF)) + " enden soll, gebe bitte \"l\" ein und best\u00E4tige!");
              left = true;
            }
          } 
        }
        if(inputV - laenge > -1){
          for (int i = 0; i < laenge+1; i++) {
            if(!validateSchiffsort(inputV-i,inputH)) {
              break;
            }
            if (i == laenge) {
              System.out.println("Wenn das Schiff auf dem Feld " + ((char) ((inputH + 65) & 0xFF)) + ((char) ((inputV - laenge + 48) & 0xFF)) + " enden soll, gebe bitte \"u\" ein und best\u00E4tige!");
              up = true;
            }
          }
        }
        if(inputH + laenge < 10){
          for (int i = 0; i < laenge+1; i++) {
            if(!validateSchiffsort(inputV,inputH+i)) {
              break;
            }
            if (i == laenge) {
              System.out.println("Wenn das Schiff auf dem Feld " + ((char) ((inputH + laenge + 65) & 0xFF)) + ((char) ((inputV + 48) & 0xFF)) + " enden soll, gebe bitte \"r\" ein und best\u00E4tige!");
              right = true;
            }
          }
        }
        if(inputV + laenge < 10){
          for (int i = 0; i < laenge+1; i++) {
            if(!validateSchiffsort(inputV+i,inputH)) {
              break;
            }
            if (i == laenge) {
              System.out.println("Wenn das Schiff auf dem Feld " + ((char) ((inputH + 65) & 0xFF)) + ((char) ((inputV + laenge + 48) & 0xFF)) + " enden soll, gebe bitte \"d\" ein und best\u00E4tige!");
              down = true;
            }
          }
        }
        if (!left&&!up&&!right&&!down) {
          System.out.println("Von dieser Position aus kann kein valides Schiff plaziert werden. Bitte wähle eine andere Startposition!");
          plaziereSchiff(laengePlus1);
        } else {
          System.out.print("> ");
          char direction = sc.next().charAt(0);
          switch (direction) {
            case 'l' : 
              if (left) {
                for (int i = 0; i < laenge+1; i++) {
                  spielfeld[1][spielerAsByte(spieler)][inputV][inputH-i] = '-';
                }
              } else {
                System.out.println("Dies ist keine valide Richtung. Bitte geben Sie die gewünschte Schiffsposition erneut an!");
                plaziereSchiff(laengePlus1);  
              } // end of if-else
              break;
            case 'u' : 
              if (up) {
                for (int i = 0; i < laenge+1; i++) {
                  spielfeld[1][spielerAsByte(spieler)][inputV-i][inputH] = '|';
                }
              } else {
                System.out.println("Dies ist keine valide Richtung. Bitte geben Sie die gewünschte Schiffsposition erneut an!");
                plaziereSchiff(laengePlus1);  
              } // end of if-else
              break;
            case 'r' : 
              if (right) {
                for (int i = 0; i < laenge+1; i++) {
                  spielfeld[1][spielerAsByte(spieler)][inputV][inputH+i] = '-';
                }
              } else {
                System.out.println("Dies ist keine valide Richtung. Bitte geben Sie die gewünschte Schiffsposition erneut an!");
                plaziereSchiff(laengePlus1);  
              } // end of if-else
              break;
            case 'd' : 
              if (down) {
                for (int i = 0; i < laenge+1; i++) {
                  spielfeld[1][spielerAsByte(spieler)][inputV+i][inputH] = '|';
                }
              } else {
                System.out.println("Dies ist keine valide Richtung. Bitte geben Sie die gewünschte Schiffsposition erneut an!");
                plaziereSchiff(laengePlus1);  
              } // end of if-else                             
              break;
            default: 
              System.out.println("Dies ist keine valide Richtung. Bitte geben Sie die gewünschte Schiffsposition erneut an!");
              plaziereSchiff(laengePlus1);
          } // end of switch
        } // end of if-else
      } else {
        System.out.println("Sie haben leider eine ung\u00FCltige Nummer eingegeben. Bitte widerholen Sie Ihre Eingabe!");
        plaziereSchiff(laengePlus1);
      } // end of if-else
    } else {
      System.out.println("Sie haben leider einen ung\u00FCltigen Buchstaben eingegeben. Bitte widerholen Sie Ihre Eingabe!");
      plaziereSchiff(laengePlus1);
    }// end of if-else
    
  }
      
  public static boolean validateSchiffsort(int feldV, int feldH){
    if (feldV - 1 > -1) {
      if(spielfeld[1][spielerAsByte(spieler)][feldV-1][feldH] != '0') {
        return false;
      }  
    }
    if (feldH - 1 > -1) {
      if(spielfeld[1][spielerAsByte(spieler)][feldV][feldH-1] != '0') {
        return false;
      }  
    }
    if (feldV + 1 < 10) {
      if(spielfeld[1][spielerAsByte(spieler)][feldV+1][feldH] != '0') {
        return false;
      }  
    }
    if (feldH + 1 < 10) {
      if(spielfeld[1][spielerAsByte(spieler)][feldV][feldH+1] != '0') {
        return false;
      }  
    }
    if(spielfeld[1][spielerAsByte(spieler)][feldV][feldH] != '0') {
      return false;
    }
    return true;
  }
      
  public static byte spielerAsByte(boolean spielerL){
    if (spielerL){
      return 1;
    }
    else {
      return 0;
    }
  }
} // end of class Schiffe_Versenken
      
      
