/*
Sa se elimine elementul de pe pozitia a n-a a unei liste liniare.

remove_nth(l1 l2 .. ln, N) =  |  []                             , n = 0
                              |  [l2 .. ln]                     , N = 1
                              |  l1 ⊕ remove_nth(l2 .. ln, N-1) , altfel
*/

% remove_nth(L: list, N: integer, Rez: list)
%           (i, i, o) (i, i, i) determinist
% L : lista din care se sterge elementul
% N : pozitia care se sterge
% Rez : lista rezultanta

% daca lista e goala, returnez lista goala
remove_nth([], _, []).

% daca s-a ajuns la N = 1, returnez lista fara primul element
remove_nth([_|T], N, T) :-
    N = 1, !.

% daca nu s-a ajuns la N = 1, iau primul element si il adaug la rezolvare
remove_nth([H|T], N, [H|R]) :-
    N \= 1,
    N1 is N-1,
    remove_nth(T, N1, R).


% run_tests.
:- begin_tests(remove_nth).

test("first position") :- remove_nth([7,8,9], 1, [8,9]).
test("middle position") :- remove_nth([7,8,9], 2, [7,9]).
test("end position") :- remove_nth([7,8,9], 3, [7,8]).
test("non-existent position") :- remove_nth([7,8,9], 4, [7,8,9]).

:- end_tests(remove_nth).
