macro m1
  inc r6
  mov r3, W
endm

m1
m1

macro m2
  rts
  jmp Line
  rts
endm

m2
m1