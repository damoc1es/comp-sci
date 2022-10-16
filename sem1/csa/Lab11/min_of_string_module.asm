bits 32
global min_of_string

segment code use32 class=code public

min_of_string: ; min_of_string(min_val, sir, n)
    ; cdecl
    ; [ESP+0]  <- adresa de revenire
    ; [ESP+4]  <- adresa lui min_val (unde trimit rezultatul)
    ; [ESP+8]  <- adresa lui sir (sirul)
    ; [ESP+12] <- valoarea lui n (lungimea sirului)
    
    cld                         ; directia de la stanga la dreapta
    mov ESI, [ESP+8]            ; ESI = adresa inceputului sirului
    mov ECX, [ESP+12]           ; ECX = cate numere sunt in sir (ramase)
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
        ;scasd                  ; se putea face si cu scasb
        not_below:
    loop for_parcurgere         ; parcurg toate numerele ramase in sir
    
    only1_min_of_string:
    mov ESI, [ESP+4]            ; ESI = adresa unde scriu rezultatul
    mov [ESI], EDX              ; minimul este scris la adresa
    
    end_min_of_string:
	ret