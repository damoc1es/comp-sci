/*
my_append(l1 l2 .. ln, r1 r2 .. rm) = | l1 l2 .. ln                         , m = 0
                                      | r1 ⊕ my_append(l1 .. ln, r2 .. rm) , altfel
my_append(L, R) = L ⊕ R
*/

% my_append(L1: list, L2: list, LRez: list)
%          (i, i, o)
my_append([], T, T).


my_append([H1|T1], T2, [H1|L]) :-
    my_append(T1, T2, L).


:- begin_tests(my_append).

test("1", [nondet]) :- my_append([], [1, 2], [1, 2]).
test("2", [nondet]) :- my_append([1, 2], [], [1, 2]).
test("3", [nondet]) :- my_append([], [], []).
test("4", [nondet]) :- my_append([1, 2], [3, 4], [1, 2, 3, 4]).

:- end_tests(my_append).

/*
Sa se scrie un predicat care substituie intr-o lista un element printr-o alta lista.

substitute_elem(l1 l2 .. ln, K, r1 r2 .. rn) = | []                                                       , n = 0
                                               | r1 r2 .. rn ⊕ substitute_elem(l2 .. ln, K, r1 r2 .. rn) , l1 = K
                                               | l1 ⊕ substitute_elem(l2 .. ln, K, r1 r2 .. rn)          , altfel
*/

% substitute_element(L1: list, K: element, L2: list, LRez)
%                   (i, i, i, o)
substitute_element([], _, _, []).


substitute_element([H|T], K, L, R2) :-
    H is K,
    substitute_element(T, K, L, R),
    my_append(L, R, R2).


substitute_element([H|T], K, L, [H|R]) :-
    not(H is K),
    substitute_element(T, K, L, R).


% run_tests.
:- begin_tests(substitute_element).

test("1", [nondet]) :- substitute_element([], 8, [1, 2], []).
test("2", [nondet]) :- substitute_element([7, 8, 9], 8, [1, 2], [7, 1, 2, 9]).
test("3", [nondet]) :- substitute_element([7, 8, 9], 8, [1], [7, 1, 9]).
test("4", [nondet]) :- substitute_element([7, 8, 9], 8, [], [7, 9]).
test("5", [nondet]) :- substitute_element([7, 8, 9], 7, [1, 2], [1, 2, 8, 9]).
test("6", [nondet]) :- substitute_element([7, 8, 9], 9, [1, 2], [7, 8, 1, 2]).
test("7", [nondet]) :- substitute_element([7, 8, 9], 6, [1, 2], [7, 8, 9]).
test("8", [nondet]) :- substitute_element([1, 1, 2, 3, 2, 5], 2, [7, 8], [1, 1, 7, 8, 3, 7, 8, 5]).

:- end_tests(substitute_element).