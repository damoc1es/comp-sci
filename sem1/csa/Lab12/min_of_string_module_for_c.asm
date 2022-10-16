bits 32
global _min_of_string

segment data use32 class=data public
    ; minim dd 0
segment code use32 class=code public

_min_of_string: ; int min_of_string(int sir[], int n);
    ; crearea cadrului de stiva
    push EBP
    mov EBP, ESP
    ;pushad
    cld                         ; directia de la stanga la dreapta
    push ESI                    ; salvez ESI, nefiind memorie volatila
    mov ESI, [EBP+8]            ; ESI = adresa inceputului sirului
    mov ECX, [EBP+12]           ; ECX = cate numere sunt in sir (ramase)
    jecxz end_min_of_string     ; daca ECX = 0 sar la finalul programului
    
    lodsd                       ; iau primul numar din sir
    mov EDX, EAX                ; initial minimul va fi primul numar
    dec ECX                     ; ECX--;
    jecxz only1_min_of_string   ; daca a fost doar un numar in sir sar la scrierea rezultatului
    
    for_parcurgere:
        lodsd                   ; EAX = numarul curent din sir
        cmp EAX, EDX            ; compar numarul cu EDX, minimul curent
        ja not_below            ; daca EAX > EDX (unsigned) atunci sar peste suprascriere
            mov EDX, EAX        ; in EDX va fi mereu minimul curent
        not_below:
    loop for_parcurgere         ; parcurg toate numerele ramase in sir
    
    only1_min_of_string:
    ; mov dword [minim], EDX

    mov EAX, EDX                ; EAX = rezultatul, minimul
    ; popad
    ; mov EAX, dword [minim]
    
    end_min_of_string:
    pop ESI                     ; restaurez ESI
    ; refacerea cadrului de stiva
    mov ESP, EBP
    pop EBP
	ret