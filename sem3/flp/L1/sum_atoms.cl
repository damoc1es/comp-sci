; sum_atoms(L: list)
; modele de flux: (i) - determinist
; L - lista a carei numere se insumeaza
(defun sum_atoms(L)
    (cond
        ((null L)          0)
        ((listp (car L))   (+ (sum_atoms (car L)) (sum_atoms (cdr L))))
        ((numberp (car L)) (+ (car L) (sum_atoms (cdr L))))
        (T                 (sum_atoms (cdr L)))
    )
)


; (sum_atoms '(1 2 3)) => 6
; (sum_atoms '(1 2 a '(4 5 b) 3)) => 15
