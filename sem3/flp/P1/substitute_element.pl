% Sa se scrie un predicat care substituie intr-o lista un element printr-o alta lista.
%
% substitute_elem(l1 l2 .. ln, K, r1 r2 .. rn) = | []                                                       , n = 0
%                                                | r1 r2 .. rn ⊕ substitute_elem(l2 .. ln, K, r1 r2 .. rn) , l1 = K
%                                                | l1 ⊕ substitute_elem(l2 .. ln, K, r1 r2 .. rn)          , altfel

substitute_element([], _, _, []).

substitute_element([H|T], K, L2, R2) :-
    H is K,
    substitute_element(T, K, L2, R),
    append(L2, R, R2).

substitute_element([H|T], K, L2, [H|R]) :-
    not(H is K),
    substitute_element(T, K, L2, R).

% TESTS:
%
% substitute_element([], 8, [1, 2], []).
% substitute_element([7, 8, 9], 8, [1, 2], [7, 1, 2, 9]).
% substitute_element([7, 8, 9], 8, [1], [7, 1, 9]).
% substitute_element([7, 8, 9], 8, [], [7, 9]).
% substitute_element([7, 8, 9], 7, [1, 2], [1, 2, 8, 9]).
% substitute_element([7, 8, 9], 9, [1, 2], [7, 8, 1, 2]).
% substitute_element([7, 8, 9], 6, [1, 2], [7, 8, 9]).
% substitute_element([1, 1, 2, 3, 2, 5], 2, [7, 8], [1, 1, 7, 8, 3, 7, 8, 5]).