; Lab. 4 / pb. 17 Se dau cuvantul A si octetul B. Sa se obtina dublucuvatul C:
; bitii 3-0 ai lui C au valoarea 1
; bitii 7-4 ai lui C coincid cu bitii 0-3 ai lui A
; bitii 13-8 ai lui C au valoarea 0
; bitii 23-14 ai lui C coincid cu bitii 13-4 ai lui A
; bitii 29-24 ai lui C coincid cu bitii 7-2 ai lui B
; bitii 31-30 au valoarea 1

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
    a dw 7CBDh ; = 14525 = 0111 1100 1011 1101b
    b db 38h ;   =    55 = 0011 1000b
    c resd 1 ;             1100 1110 1111 0010 1100 0000 1101 1111b = CEF2 C0DFh

; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov dword[c],0000000Fh ; c = 0000 0000 0000 0000 0000 0000 0000 1111b
        ;and [c],0000000Fh       ; c = 0000 0000 0000 0000 0000 0000 0000 1111b
        
        ror dword[c],4 ; rotesc 4 biti la dreapta c = F0000000h = 1111 0000 0000 0000 0000 0000 0000 0000b
        mov AX,[a] ; AX = a = 7CBDh = 0111 1100 1011 1101b
        and AX, 000Fh ; zerorizez AX mai putini bitii 3-0 AX = 000Dh = 0000 0000 0000 1101b
        or byte[c],AL ; ultimii 4 biti c = F000000Dh = 1111 0000 0000 0000 0000 0000 0000 1101b
        rol dword[c],4 ; rotesc 4 biti la stanga c = 000000DFh = 0000 0000 0000 0000 0000 0000 1101 1111b
        ; deja bitii 13-8 a lui c au valoarea 0h
        
        ror dword[c],14 ; rotesc cu 14 biti la dreapta c = 00 0000 1101 1111 0000 0000 0000 0000 00b = 037C 0000h
        ror word[a],4 ; rotesc cu 4 biti la dreapta a = 1101 0111 1100 1011b = D7CBh
        
        mov AX,[a] ; AX = a = 1101 0111 1100 1011b = D7CBh
        and AX,0000001111111111b ; zerorizez totul in afara de ultimii 10 biti AX = 0000 0011 1100 1011b = 03CBh
        or word[c],AX ; bitii 23-14 a lui c = bitii 13-4, c = 00 0000 1101 1111 0000 0000 1111 0010 11b = 037C 03CBh
        
        ror dword[c],10 ; rotesc cu 10 biti la dreapta ca sa ajung la ultimii 10 biti c = 1111 0010 1100 0000 1101 1111 0000 0000b = F2C0 DF00h
        ror byte[b],2 ; rotesc cu 2 biti la dreapta ca sa ajung mai rapid la bitii 7-2 b = 0000 1110b = 0Eh
        mov AL,[b] ; AL = b = 0000 1110b = 0Eh
        
        and AL,00111111b ; zerorizez bitii 8-7 (initial 1-0) ; AL = 0000 1110b = 0Eh
        or byte[c],AL ; bitii 29-24 a lui c = bitii 7-2 a lui b, c = 1111 0010 1100 0000 1101 1111 0000 1110b = F2C0 DF0Eh
        or byte[c],11000000b ; bitii 31-30 = 1, c = 1111 0010 1100 0000 1101 1111 1100 1110b = F2C0 DFCEh
        
        ror dword[c],8 ; rotesc cu 8 biti la dreapta ca sa ajung la pozitiile initiale
            ; c = 1100 1110 1111 0010 1100 0000 1101 1111b = CEF2 C0DFh
        mov EAX,[c]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
