/*
is_prime(n, d) = | False            , n < 2
                 | True             , n = d
                 | False            , n != d ∧ n % d = 0
                 | is_prime(n, d+1) , altfel

remove_seq(l1 l2 l3 .. ln, SECV) = | []                            , n = 0
                                   | []                            , n = 1 si SECV = 1
                                   | [l1]                          , n = 1 si SECV = 0
                                   | remove_seq(l2 l3 .. ln, 1)       , is_prime(l1) si is_prime(l2) si n >= 2
                                   | remove_seq(l2 l3 .. ln, 0)       , is_prime(l1) si !is_prime(l2) si SECV = 1
                                   | l1 + remove_seq(l2 l3 .. ln, 0)  , altfel

remove_seq_W(L) = remove_seq(L, 0)
*/

% is_prime(N: integer, D: integer)
% model de flux: (i, i) determinist
% N - numarul verificat daca este prim
% D - divizorul de la care am ajuns (2)
is_prime(N, _) :-
    N < 2, !,
    fail.

is_prime(N, N) :- !.

is_prime(N, D) :-
    0 is mod(N, D), !,
    fail.

is_prime(N, D) :-
    D1 is D+1,
    is_prime(N, D1).


% remove_seq(L: list, SECV: integer, R: list)
% modele de flux: (i, i, o) determinist
%                 (i, i, i) determinist
% L - lista initiala din care eliminam secventele de numere prime
% SECV - contorizam daca ne aflam intr-o secventa (1) sau nu ne aflam inca (0)
% R - lista rezultanta, fara secventele de numere prime
remove_seq([], _, []) :- !.

remove_seq([_], 1, []) :- !.

remove_seq([H1], 0, [H1]) :- !.

remove_seq([H1, H2 | T], _, R) :-
    is_prime(H1, 2),
    is_prime(H2, 2),
    !,
    remove_seq([H2|T], 1, R).

remove_seq([H1, H2 | T], 1, R) :-
    is_prime(H1, 2),
    not(is_prime(H2, 2)),
    !,
    remove_seq([H2|T], 0, R).

remove_seq([H1, H2 | T], _, [H1|R]) :-
    remove_seq([H2|T], 0, R), !.


% remove_seq_W(L: list, R: list)
% modele de flux: (i, o) determinist
%                 (i, i) determinist
% L - lista initiala din care eliminam secventele de numere prime
% R - lista rezultanta, fara secventele de numere prime
remove_seq_W(L, R) :-
    remove_seq(L, 0, R).


:-begin_tests(remove_seq).

test("1") :- remove_seq_W([2, 3, 4, 5, 6, 7, 13], [4, 5, 6]).
test("2") :- remove_seq_W([2, 3, 5, 7, 11, 1741], []).
test("3") :- remove_seq_W([4, 6, 8, 10], [4, 6, 8, 10]).
test("4") :- remove_seq_W([2, 307, 6, 8, 173, 7, 13, 10, 9], [6, 8, 10, 9]).

:-end_tests(remove_seq).
