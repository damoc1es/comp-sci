; Apeluri funcții de sistem / pb. 17: Sa se citeasca de la tastatura un numar in baza 10 si sa se afiseze valoarea acelui numar in baza 16

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll     ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll   ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    n dd 0 ; variabila in care stocam numarul
    mesaj_input db "n in baza 10 = ", 0     ; mesajul inainte afisat inainte de citire
    format_input db "%d", 0                 ; formatul cu care il citim pe n
    mesaj_output db "n in baza 16 = %X", 0  ; mesajul rezultat, %X insemnand ca afisam sub forma de numar in baza 16 (uppercase)
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; printf("n in baza 10 = ")
        push dword mesaj_input      ; punem in stiva adresa mesajului
        call [printf]               ; apelam functia printf pentru afisare
        add ESP, 4*1                ; eliberam de pe stiva un dword
        
        ; scanf("%d", &n)
        push dword n                ; punem in stiva adresa lui n (unde stocam numarul)
        push dword format_input     ; punem in stiva adresa formatului in care se citeste numarul (%d - numar in baza 10)
        call [scanf]                ; apelam functia scanf pentru citire
        add ESP, 4*2                ; eliberam de pe stiva 2 dword-uri
        
        ; printf("n in baza 16 = %X", n)
        push dword [n]              ; punem in stiva valoarea lui n
        push dword mesaj_output     ; punem in stiva adresa formatului mesajului rezultat
        call [printf]               ; apelam functia printf pentru a afisa rezultatul
        add ESP, 4*2                ; eliberam de pe stiva 2 dword-uri
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
