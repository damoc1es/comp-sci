; Lab. 4 / pb. 13 Dandu-se 4 octeti, sa se obtina in AX suma numerelor intregi reprezentate de bitii 4-6 ai celor 4 octeti.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a db 17 ; a = 11h = 0001 0001b
    b db 63 ; b = 3Fh = 0011 1111b
    c db 36 ; c = 24h = 0010 0100b
    d db 24 ; d = 18h = 0001 1000b
    
; AX ar trb sa fie

; our code starts here
segment code use32 class=code
    start:
        ; ...

        sar byte[a],4 ; shift la dreapta pastrand semnul a = 0000 0001b = 01h
        sar byte[b],4 ; shift la dreapta pastrand semnul b = 0000 0011b = 03h
        sar byte[c],4 ; shift la dreapta pastrand semnul c = 0000 0010b = 02h
        sar byte[d],4 ; shift la dreapta pastrand semnul d = 0000 0001b = 01h
        
        mov BX,0000h ;    BX = 0000 0000 0000 0000b
        mov AL,[a] ; AL = a = 01h = 1
        cbw ; AL -> AX 
        add BX,AX ; BX = BX+AX = 1h = 0000 0000 0000 0001b
        
        mov AL,[b] ; AL = b = 03h = 3 = 0000 0011b
        cbw ; AL -> AX = 0003h = 0000 0000 0000 0011b
        add BX,AX ; BX = BX+AX = a+b = 1+3 = 4h = 0000 0000 0000 0100b
        
        mov AL,[c] ; AL = c = 02h = 2 = 0000 0010b
        cbw ; AL -> AX = 0002h = 0000 0000 0000 0010b
        add BX,AX ; BX = BX+AX = a+b+c = 4+2 = 6h = 0000 0000 0000 0110b
        
        mov AL,[d] ; AL = d = 01h = 1
        cbw ; AL -> AX = 0001h = 0000 0000 0000 0001b
        add BX,AX ; BX = BX+AX = a+b+c+d = 6+1 = 7h = 0000 0000 0000 0111b
        
        mov AX,BX ; AX = BX = 7h = 0000 0000 0000 0111b
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
