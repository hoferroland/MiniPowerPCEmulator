100 CLR 0        '100:Akku zu 0 setzen
102 LWDD 2 #522  '102:1 ins Register 2 legen
104 SWDD 0 #504  '104:Ergebnisspeicherzelle 504 initialisieren
106 SWDD 0 #506  '106:Ergebnisspeicherzelle 506 initialisieren
108 SWDD 0 #512  '108:Zwischenspeicherzelle 512 initialisieren
110 SWDD 0 #508  '110:Speicherzelle 508 fuer Restsumme lower initialisieren
112 SWDD 0 #510  '112:Speicherzelle 510 fuer Restsumme upper initialisieren
114 LWDD 0 #502  '114:Operand 2 in Akku laden
116 BZD #288     '116:Akku = 0 dann springe zum Ende
118 LWDD 1 #520  '118:Lade -32768 in Register 1
120 AND 1        '120:Bitweise AND mit Register 1 und Akku -> Op2 = positiv, dann wird Akku leer, sonst Op2 = negativ
122 BZD #134     '122:Sprung zu Vorzeichen Operand 1 merken
124 LWDD 0 #502  '124:Falls Op2 negativ, Op2 nochmals in Akku laden
126 NOT          '126:Alle Bit negieren (Op2 positiv machen)
128 INC          '128:Akku um 1 erhoehen (2er-Komplement)
130 SWDD 0 #502  '130:Positiver Op2 in Zelle 502 abspeichern
132 SWDD 2 #512  '132:Vorzeichen Op2 merken (in Zelle 512 eine 1 aus Reg2 abspeichern)
134 LWDD 0 #500  '134:Operand 1 in Akku laden
136 BZD #280     '136:Akku = o dann springe zum Ende
138 AND 1        '138:Bitweise AND mit (-32768) und Akku -> Op1 = positiv, dann wird Akku leer, sonst Op1 = negativ
140 BZD #156     '140:Op1 positiv, dann Sprung zur Multiplikation
142 LWDD 0 #500  '142:Falls Op1 negativ, Op1 nochmals in Akku laden
144 NOT          '144:Alle Bit negieren (Op1 positiv machen)
146 INC          '146:Akku um 1 erhoehen (2er-Komplement)
148 SWDD 0 #500  '148:Positiver Op1 in Zelle 500 abspeichern
150 LWDD 0 #512  '150:Lade Vorzeichen-Garage in Akku
152 ADDD #1      '152:Addiere Vorzeichen hinzu
154 SWDD 0 #512  '154:Vorzeichen Op1 merken (in Zelle 512 Akku abspeichern)
156 LWDD 0 #500  '156:Operand 1 in Akku laden
158 SRL          '158:Logisch schieben nach rechts LSB = Carry-Flag
160 SWDD 0 #500  '160:Speichere Rest-Op1 in Zelle 500
162 BCD #188     '162:Falls Carry-Flag = 1, springe zu MulEinsRechts
164 LWDD 0 #502  '164:Lade Op 2 in Akku
166 SLL          '166:Multipliziere Op2 mit 2 (schieben nach links)
168 SWDD 0 #502  '168:Speichere Resultat als neuen Op2 in Zelle 502
170 BCD #178     '170:Falls Carry-Flag aus 166 gesetzt, gehe zu Schritt 178
172 LWDD 0 #506  '172:Lade Wert upper in Akku
174 SLL          '174:Multipliziere upper mit 2 (schieben nach links), bei 0 passiert nix
176 BD #184      '176:Springe zu speichern upper
178 LWDD 0 #506  '178:Lade Wert upper in Akku
180 SLL          '180:upper mit 2 multiplizieren (schieben nach links)
182 INC          '182:Infolge Overflow um 1 erhoehen
184 SWDD 0 #506  '184:Speichere Akku Zelle 506 fuer neuen upper Wert
186 BD #156      '186:Sprung zu Multiplikation
188 BZD #218     '188:Falls Akku leer, Sprung zu Addiere Restsumme Lower zu Op2
190 LWDD 0 #502  '190:Op2 lower in Akku laden
192 LWDD 1 #508  '192:Restsumme lower in Reg 1 laden
194 ADD 1        '194:addiere Restsumme lower zu Akku
196 SWDD 0 #508  '196:Speichere neuen Wert Restsumme lower
198 LWDD 0 #506  '198:Lade res upper in Akku
200 LWDD 1 #510  '200:Lade Restsumme upper in Reg 1
202 BCD #210     '202:Falls Carry-Flag = 1, Sprung zu INC Lower Overlow
204 ADD 1        '204:Falls Carry-Flag = 0, addiere upper mit Restsumme upper
206 SWDD 0 #510  '206:Speichere neuen Wert Restsumme upper
208 BD #164      '208:Springe zu MulNullRechts
210 INC          '210:Bei Overflow bei Lower Add, Akku um 1 erhoehen
212 ADD 1        '212:Addiere Akku mit Restsumme upper
214 SWDD 0 #510  '214:Speichere neuen Wert Restsumme upper
216 BD #164      '216:Springe zu MulNullRechts
218 LWDD 0 #502  '218:Lade aktuellen Op2-Wert in Akku
220 LWDD 1 #508  '220:Lade Restsumme Lower in Reg 1
222 ADD 1        '222:Addiere Restsumme Lower zu aktuellem Op2-Wert
224 SWDD 0 #504  '224:Res Lower von OP2 uebernehmen
226 BCD #230     '226:Falls Carry-Flag = 1, Sprung zu INC Upper Restsumme
228 BD #242      '228:Sprung zu Upper Restsumme zu Op2 Upper addieren
230 LWDD 0 #510  '230:INC Upper Restsumme - Lade Restsumme upper in Akku
232 INC          '232:Restsumme upper in Akku um 1 erhoehen
234 LWDD 1 #506  '234:Lade aktuellen Wert res upper in Reg1
236 ADD 1        '236:Addiere Restsumme upper mit res upper
238 SWDD 0 #506  '238:Speichere neuen Wert in res upper in Zelle 506
240 BD #250      '240:Sprung zu Vorzeichen setzen
242 LWDD 0 #506  '242:Lade aktuellen Wert res-upper in Akku
244 LWDD 1 #510  '244:Lade aktuellen Wert Restsumme-upper in Reg1
246 ADD 1        '246:Addiere Restsumme upper in Reg1 zu res-upper in Akku
248 SWDD 0 #506  '248:Speichere neuen Wert res-upper in Zelle 506
250 LWDD 0 #512  '250:Lade Vorzeichen-Garage in Akku
252 AND 2        '252:die 1 aus Reg2 zum Akku addieren
254 BZD #288     '254:Falls Akku leer - springe zum Ende
256 LWDD 0 #504  '256:Lade res lower in Akku
258 NOT          '258:Bitweise negieren (Annahme *-1 funktioniert beidseitig)
260 INC          '260:Akku um 1 erhoehen (2er-Komplement)
262 SWDD 0 #504  '262:neuen Wert res lower in Zelle 504 speichern
264 LWDD 0 #506  '264:Lade res upper in Akku
266 BCD #282     '266:Falls Carry-Flag = 1 springe Lower-INC-Overflow
268 BZD #276     '268:Falls Akku = 0, Sprung zu Upper-Zero-INV
270 NOT          '270:Falls beim Laden von res lower kein Overflow, Bitweise negieren
272 SWDD 0 #506  '272:neuen Wert res lower in Zelle 506 speichern
274 BD #288      '274:Sprung zum Ende
276 NOT          '276:Upper-Zero-INV, res upper Bitweise negieren
278 SWDD 0 #506  '278:neuen Wert res upper in Zelle 506 speichern
280 BD #288      '280:Sprung zum Ende
282 NOT          '282:Lover-INV-Overflow, falls Carry-Flag aus LWDD#506=1, Akku Bitweise negieren
284 INC          '284:sowie Akku um 1 erhoehen (2er-Komplement)
286 SWDD 0 #506  '286:neuen Wert res lower in Zelle 506 speichern
288 END          '288:Ende des Programms 