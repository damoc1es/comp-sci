function t=check_triangle(P1, P2, P3)
    % t = 1 -> acute triangle
    % t = 0 -> right triangle
    % t = -1 -> obtuse triangle

    sides = [pdist([P1;P2]) pdist([P2;P3]) pdist([P3;P1])];

    sides = sort(sides).^2;

    if sides(1) + sides(2) > sides(3)
        t = 1;
    elseif sides(1) + sides(2) == sides(3)
        t = 0;
    else
        t = -1;
    endif
end
