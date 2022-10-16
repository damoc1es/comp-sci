; pb. 17 / var1: parcurgere in sens direct; Se da un sir de dublucuvinte. Sa se ordoneze descrescator sirul cuvintelor inferioare ale acestor dublucuvinte. Cuvintele superioare raman neschimbate.
; Exemplu:
; sir DD 12345678h, 1256ABCDh, 12AB4344h
; rez:   1234ABCDh, 12565678h, 12AB4344h

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
    ;          ____       ____       ____
    sir dd 12345678h, 1256ABCDh, 12AB4344h ; sirul initial
    lend equ ($-sir)/4 ; lungimea dublucuvintelor
    lenw equ lend*2 ; lungimea cuvintelor
    
    rez times lend dw 0
        ; 1234ABCDh, 12565678h, 12AB4344h
        ; in memorie: CD AB 34 12 78 56 56 12 44 43 AB 12
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ESI, sir ; mutam indexul la sirul initial
        mov EDI, rez ; rez va fi sirul in care facem sortarea
        
        cld ; DF = 0, mergem de la stanga la dreapta
        mov ECX, lend
        repeta:
            movsw ; la adresa <ES:EDI> se incarca cuvantul de la adresa <DS:ESI> (mut fiecare cuvant inferior al dublucuvintelor in rez)
            inc ESI ; incrementam de 2 ori (in plus fata de ce face movsw) deoarece sir e dword si vrem doar cuvintele inferioare
            inc ESI ; deci sarim peste cuvantul superior
        loop repeta
        
        mov EAX, 1 ; presupunem sirul nu este sortat (EAX = ok)
        sort: ; sort pe rez (cuvinte)
            mov ESI, rez ; aducem ESI la inceput
            cmp EAX, 0 ; comparam EAX cu 0
            je final ; daca EAX e 0 atunci tot sirul este ordonat corespunzator
            mov EAX, 0 ; EAX = 0
            mov ECX, lend-1 ; comparam elementele
            pentru:
                mov DX, [ESI] ; DX = primul element
                cmp DX, [ESI+2] ; compar cu urmatorul cuvant
                jae continua ; DX <= [ESI] executa
                    mov BX, [ESI+2] ; interschimbam cuvintele
                    mov [ESI+2], DX
                    mov [ESI], BX
                    mov EAX, 1 ; marcam faptul ca s-a produs o schimbare => nu e sortat complet
                continua:
                inc ESI ; incrementam de doua ori deoarece lucram cu cuvinte
                inc ESI ; si noi avem stocate dublucuvinte, sarim peste un cuvant
            loop pentru 
        jmp sort
            
        final: ; rez e sortat descrescator
        
        mov ECX, lend ; ECX = lungimea sirului
        mov ESI, rez ; ESI - unde incepe rez
        mov EDI, sir ; EDI - unde incepe sir
        
        repeta2:
            movsw ; la adresa <ES:EDI> se incarca cuvantul de la adresa <DS:ESI> (inlocuiesc cuvintele inferioare ale lui "sir" cu cele din "rez")
            inc EDI ; incrementam de 2 ori (in plus fata de ce face movsw) deoarece lucram pe cuvinte
            inc EDI ; deci sarim peste cuvantul superior
        loop repeta2
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
