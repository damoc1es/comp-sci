/*
Sa se elimine elementul de pe pozitia a n-a a unei liste liniare.

remove_nth(l1 l2 .. ln, N) =  |  []                             , n = 0
                              |  [l2 .. ln]                     , N = 1
                              |  l1 ⊕ remove_nth(l2 .. ln, N-1) , altfel
*/

% remove_nth(L: list, N: integer, Rez: list)
%           (i, i, o)
remove_nth([], _, []).


remove_nth([_|T], N, T) :-
    1 is N.


remove_nth([H|T], N, [H|R]) :-
    not(1 is N),
    N1 is N-1,
    remove_nth(T, N1, R).


% run_tests.
:- begin_tests(remove_nth).

test("first position", [nondet]) :- remove_nth([7,8,9], 1, [8,9]).
test("middle position", [nondet]) :- remove_nth([7,8,9], 2, [7,9]).
test("end position", [nondet]) :- remove_nth([7,8,9], 3, [7,8]).
test("non-existent position", [nondet]) :- remove_nth([7,8,9], 4, [7,8,9]).

:- end_tests(remove_nth).
