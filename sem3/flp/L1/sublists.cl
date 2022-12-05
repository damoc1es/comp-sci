; sublists(L: list)
; L - lista a carei subliste se returneaza
(defun sublists(L)
    (cond
        ((null L)        NIL)
        ((listp (car L)) (cons (car L) (append (sublists (car L)) (sublists (cdr L)))))
        (T               (sublists (cdr L)))
    )
)


; (sublists '((1 2 3) ((4 5) 6))) => ((1 2 3) (4 5) ((4 5) 6))
; (sublists '(1 4 (5 6) (3 1))) => ((5 6) (3 1))
