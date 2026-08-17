int inf(int a, int b)
{
    return a <= b;
}

int sup(int a, int b)
{
    return a >= b;
}

int pair_croissant(int a, int b)
{
    if (a%2 == 0 && b%2 == 0)
        return a < b;
    else if (a%2 == 0 && b%2 != 0)
        return 1;
    else if (a%2 == 1 && b%2 == 0)
        return 0;
    else
        return a < b;
}

int impair_croissant(int a, int b)
{
    if (a%2 == 0 && b%2 == 0)
        return a < b;
    else if (a%2 == 0 && b%2 != 0)
        return 0;
    else if (a%2 == 1 && b%2 == 0)
        return 1;
    else
        return a < b;
}
