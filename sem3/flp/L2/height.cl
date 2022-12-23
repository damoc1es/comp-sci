; visit(arb: list, nv: int, nm: int, type: int, col: list)
; arb - arborele parcurs
; nv - numarul de varfuri
; nm - numarul de muchii
; type - tipul parcurgerii (0 = stanga, 1 = dreapta)
; col - colector
(defun visit(arb nv nm type col)
    (cond
        ((null arb) col)
        ((= nv (+ 1 nm)) 
            (cond
                ((= type 1) arb)
                (t col)
            )
        )
        (T (visit (cddr arb) (+ 1 nv) (+ (cadr arb) nm) type (append col (list (car arb) (cadr arb)))))
    )
)


; left(arb: list)
; arb - arborele a carui subarbore stang se parcurge
(defun left(arb)
    (visit (cddr arb) 0 0 0 NIL)
)


; right(arb: list)
; arb - arborele a carui subarbore drept se parcurge
(defun right(arb)
    (visit (cddr arb) 0 0 1 NIL)
)


; height(arb: list)
; arb - arborele a carui numar de nivele se calculeaza
(defun height(arb)
    (cond
        ((null arb) 0)
        (T (+ 1 (max (height (left arb)) (height (right arb)))))
    )
)


; (height '(A 0)) => 1
; (height '(A 2 B 0 C 0)) => 2
; (height '(A 2 B 0 C 2 D 0 E 0)) => 3
; (height '(A 2 B 2 C 1 I 0 F 1 G 0 D 2 E 0 H 0)) => 4
