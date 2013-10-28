'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Assembler-Programm Multiplikation                                               '
'                                                                                 '
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Autor: Roland Hofer; Dam-Hoon Lee - Klasse 3Ib                                  '
'                                                                                 '
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Definitionen                                                                    '
'                                                                                 '
' 500 		=  	Operand 1                                                         '
' 502		=	Operand 2                                                         '
' 504		=	res lower                                                         '
' 506		=	res upper                                                         '
' 508		=	restsumme lower                                                   '
' 510		=	restsumme upper                                                   '
' 512		=	Vorzeichen-Garage                                                 ' 
' 520-540	=	Konstanten                                                        '
'                                                                                 '
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

' 000: Definieren Eingabewerte
	500=-222         'Eingabe Operand 1
	502=-333         'Eingabe Operand 2
	520=-32768     'Eingabe max Minuszahl (16-Bit = -32768)
	522=1          'Benoetigt fuer LSB-Check

' 100: Initialisierung
	CLR 0        '100:Akku zu 0 setzen
	LWDD 2 #522  '102:1 ins Register 2 legen
	SWDD 0 #504  '104:Ergebnisspeicherzelle 504 initialisieren
	SWDD 0 #506  '106:Ergebnisspeicherzelle 506 initialisieren
	SWDD 0 #512  '108:Zwischenspeicherzelle 512 initialisieren
	SWDD 0 #508  '110:Speicherzelle 508 fuer Restsumme lower initialisieren
	SWDD 0 #510  '112:Speicherzelle 510 fuer Restsumme upper initialisieren

' 114: Vorzeichen Operand 2 merken
	LWDD 0 #502  '114:Operand 2 in Akku laden
	BZD #288     '116:Akku = 0 dann springe zum Ende
	LWDD 1 #520  '118:Lade -32768 in Register 1
	AND 1        '120:Bitweise AND mit Register 1 und Akku -> Op2 = positiv, 
                 '    dann wird Akku leer, sonst Op2 = negativ
	BZD #134     '122:Sprung zu Vorzeichen Operand 1 merken
	LWDD 0 #502  '124:Falls Op2 negativ, Op2 nochmals in Akku laden
	NOT          '126:Alle Bit negieren (Op2 positiv machen)
	INC          '128:Akku um 1 erhoehen (2er-Komplement)
	SWDD 0 #502  '130:Positiver Op2 in Zelle 502 abspeichern
	SWDD 2 #512  '132:Vorzeichen Op2 merken (in Zelle 512 eine 1 aus Reg2 abspeichern)

' 134: Vorzeichen Operand 1 merken
	LWDD 0 #500  '134:Operand 1 in Akku laden
	BZD #280     '136:Akku = o dann springe zum Ende
	AND 1        '138:Bitweise AND mit (-32768) und Akku -> Op1 = positiv,
                 '    dann wird Akku leer, sonst Op1 = negativ
	BZD #156     '140:Op1 positiv, dann Sprung zur Multiplikation
	LWDD 0 #500  '142:Falls Op1 negativ, Op1 nochmals in Akku laden
	NOT          '144:Alle Bit negieren (Op1 positiv machen)
	INC          '146:Akku um 1 erhoehen (2er-Komplement)
	SWDD 0 #500  '148:Positiver Op1 in Zelle 500 abspeichern
	LWDD 0 #512  '150:Lade Vorzeichen-Garage in Akku
	ADDD #1      '152:Addiere Vorzeichen hinzu
	SWDD 0 #512  '154:Vorzeichen Op1 merken (in Zelle 512 Akku abspeichern)

'156: Multiplikation
	LWDD 0 #500  '156:Operand 1 in Akku laden
	SRL          '158:Logisch schieben nach rechts LSB = Carry-Flag
	SWDD 0 #500  '160:Speichere Rest-Op1 in Zelle 500
	BCD #188     '162:Falls Carry-Flag = 1, springe zu MulEinsRechts

'164: MulNullRechts: Carry-Flag aus Multiplikation = 0
	LWDD 0 #502  '164:Lade Op 2 in Akku
	SLL          '166:Multipliziere Op2 mit 2 (schieben nach links)
	SWDD 0 #502  '168:Speichere Resultat als neuen Op2 in Zelle 502
	BCD #178     '170:Falls Carry-Flag aus 166 gesetzt, gehe zu Schritt 178
	LWDD 0 #506  '172:Lade Wert upper in Akku
	SLL          '174:Multipliziere upper mit 2 (schieben nach links), bei 0 passiert nix
	BD #184      '176:Springe zu speichern upper
	LWDD 0 #506  '178:Lade Wert upper in Akku
	SLL          '180:upper mit 2 multiplizieren (schieben nach links)
	INC          '182:Infolge Overflow um 1 erhoehen
	SWDD 0 #506  '184:Speichere Akku Zelle 506 fuer neuen upper Wert
	BD #156      '186:Sprung zu Multiplikation

'188: MulEinsRechts: Carry-Flag aus Multiplikation = 1
	BZD #218     '188:Falls Akku leer, Sprung zu Addiere Restsumme Lower zu Op2
	LWDD 0 #502  '190:Op2 lower in Akku laden
	LWDD 1 #508  '192:Restsumme lower in Reg 1 laden
	ADD 1        '194:addiere Restsumme lower zu Akku
	SWDD 0 #508  '196:Speichere neuen Wert Restsumme lower
	LWDD 0 #506  '198:Lade res upper in Akku
	LWDD 1 #510  '200:Lade Restsumme upper in Reg 1
	BCD #210     '202:Falls Carry-Flag = 1, Sprung zu INC Lower Overlow
	ADD 1        '204:Falls Carry-Flag = 0, addiere upper mit Restsumme upper
	SWDD 0 #510  '206:Speichere neuen Wert Restsumme upper
	BD #164      '208:Springe zu MulNullRechts
	INC          '210:Bei Overflow bei Lower Add, Akku um 1 erhoehen
	ADD 1        '212:Addiere Akku mit Restsumme upper
	SWDD 0 #510  '214:Speichere neuen Wert Restsumme upper
	BD #164      '216:Springe zu MulNullRechts

'218: Addiere Restsumme Lower zu Op2
	LWDD 0 #502  '218:Lade aktuellen Op2-Wert in Akku
	LWDD 1 #508  '220:Lade Restsumme Lower in Reg 1
	ADD 1        '222:Addiere Restsumme Lower zu aktuellem Op2-Wert
	SWDD 0 #504  '224:Res Lower von OP2 uebernehmen
	BCD #230     '226:Falls Carry-Flag = 1, Sprung zu INC Upper Restsumme
	BD #242      '228:Sprung zu Upper Restsumme zu Op2 Upper addieren
	LWDD 0 #510  '230:INC Upper Restsumme - Lade Restsumme upper in Akku
	INC          '232:Restsumme upper in Akku um 1 erhoehen
	LWDD 1 #506  '234:Lade aktuellen Wert res upper in Reg1
	ADD 1        '236:Addiere Restsumme upper mit res upper
	SWDD 0 #506  '238:Speichere neuen Wert in res upper in Zelle 506
	BD #250      '240:Sprung zu Vorzeichen setzen

'242: Upper Restsumme zu Op2 Upper addieren
	LWDD 0 #506  '242:Lade aktuellen Wert res-upper in Akku
	LWDD 1 #510  '244:Lade aktuellen Wert Restsumme-upper in Reg1
	ADD 1        '246:Addiere Restsumme upper in Reg1 zu res-upper in Akku
	SWDD 0 #506  '248:Speichere neuen Wert res-upper in Zelle 506

'250: Setze Vorzeichen
	LWDD 0 #512  '250:Lade Vorzeichen-Garage in Akku
	AND 2        '252:die 1 aus Reg2 zum Akku addieren
	BZD #288     '254:Falls Akku leer - springe zum Ende
	LWDD 0 #504  '256:Lade res lower in Akku
	NOT          '258:Bitweise negieren (Annahme *-1 funktioniert beidseitig)
	INC          '260:Akku um 1 erhoehen (2er-Komplement)
	SWDD 0 #504  '262:neuen Wert res lower in Zelle 504 speichern
	LWDD 0 #506  '264:Lade res upper in Akku
	BCD #282     '266:Falls Carry-Flag = 1 springe Lower-INC-Overflow
	BZD #276     '268:Falls Akku = 0, Sprung zu Upper-Zero-INV
	NOT          '270:Falls beim Laden von res lower kein Overflow, Bitweise negieren
	SWDD 0 #506  '272:neuen Wert res lower in Zelle 506 speichern
	BD #288      '274:Sprung zum Ende
	NOT          '276:Upper-Zero-INV, res upper Bitweise negieren
	SWDD 0 #506  '278:neuen Wert res upper in Zelle 506 speichern
	BD #288      '280:Sprung zum Ende
	NOT          '282:Lover-INV-Overflow, falls Carry-Flag aus LWDD#506=1, Akku Bitweise negieren
	INC          '284:sowie Akku um 1 erhoehen (2er-Komplement)
	SWDD 0 #506  '286:neuen Wert res lower in Zelle 506 speichern

'288: Programm-Ende
	END         '288:Ende des Programms
	
	

	


 