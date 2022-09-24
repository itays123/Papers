; this should NOT generate an object file
; command & symbol errors
.string "hello"
label: unrec #34, r10
multCommas: add #34, ,r10
missingComma: add #34 r10
notFoundFirst: jmp
notFoundSec: add #34
notFoundSec2: add #34    ,
invldNum: add #xyz, r10
invldReg: add #-6, symb[xyz]
toolongsymbolabcdefghijklmnopqrstuvwxyz: rts
illegal_symb: jmp symb
0strisawesome: rts
r10: add #-6, r10
add: add #-6, r10
Add: add #-6, r10
macro: add #-6, r10
endm: add #-6, r10
jmp symb]r10[
jmp symb[r16]
add #34, #22
jmp #55
add #-6, r10,
add #-6, r10    ,
add #-6, r10    hey!
; data errors
.unrec "Hi!"
.data
.data ,1,2,3,4
.data 1,2,3,invlid
.data 1,2,3,
.data 1,2,3   ,  
.data 1,2,3   .
.string
.string Hi!"
.string Hi!
.string "Hi!
; operation errors
rts #8
jmp , symb
jmp
jmp symb[r8]
mov #48
mov #48, 
mov #48, symb[r6]
mov #48, symb,
mov #48, symb   ,
mov #48, symb, Hi!
jmp symb,
jmp symb   ,
jmp #48
mov #48, #48
jmp r3
; Entry and extern errors
.entry
.entry ,symb
.extern symb symb2
.extern symb,
.extern symb   , 
.extern symb, symb2