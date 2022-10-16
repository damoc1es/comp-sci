; pb. 25 / Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D ce contine toate elementele din S1 care nu apar in S2.
; Exemplu:
; S1: '+', '4', '2', 'a', '8', '4', 'X', '5'
; S2: 'a', '4', '5'
;  D: '+', '2', '8', 'X'

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
    S1 db '+', '4', '2', 'a', '8', '4', 'X', '5'
    len equ $-S1 ; lungimea sirului S1
    S2 db 'a', '4', '5'
    len2 equ $-S2 ; lungimea sirului S2
     D times len db 0
    ;     '+', '2', '8', 'X'
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ECX, len ; punem lungimea sirului in ecx
        mov ESI, 0 ; indexul de la care incepe sirul S1
        mov EDX, 0 ; indexul de la care incepe sirul D
        jecxz Sfarsit ; se ajunge la Sfarsit atunci cand ECX == 0
        Repeta:
            mov AL, [S1+ESI] ; mutam in AL octetul cu indexul ESI din sirul S1 
            
            push ECX ; pastram ECX in stiva
            push ESI ; pastram ESI in stiva
            mov ECX, len2 ; punem in ECX lungimea lui S2
            mov ESI, 0 ; resetam ESI
            jecxz nuEsteInSir ; se ajunge la nuEsteInSir at cand ECX == 0
            Repeta2:
                mov BL, [S2+ESI] ; mutam in BL octetul cu indexul ESI din sirul S2
                cmp AL,BL ; comparam AL, BL
                je esteInSir ; daca AL si BL sunt egale atunci jump la esteInSir
                inc ESI ; incrementam ESI
            loop Repeta2 ; while
            nuEsteInSir:
                mov [D+EDX], AL ; punem caracterul ce corespunde cerintei
                inc EDX ; incrementam "indexul" EDX (= cate caractere corecte am gasit pana acum)
            esteInSir:
            pop ESI ; luam inapoi ESI din stiva
            pop ECX ; luam inapoi ECX din stiva
            inc ESI ; incrementam indexul ESI
        loop Repeta
        Sfarsit:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
