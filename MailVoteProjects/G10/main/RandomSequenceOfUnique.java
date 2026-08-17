package main;

/*
    All credit goes to @Jeff Preshing
    git: https://github.com/preshing/RandomSequence
    
    ++O(1) time et memory complexity
    ++up to 2^32 repeat-free Long 
*/


import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;


class RandomSequenceOfUnique
{
    private long m_index;
    private long m_intermediateOffset;
    public long seedBase;

    private static long permuteQPR(long x)
    {
        long prime = 104743L;

        if (x >= prime){
            return x;  // The 5 Longs out of range are mapped to themselves.
        }
        else{
            long residue = (x * x) % prime;
            return (x <= prime / 2) ? residue : prime - residue;
        }    
    }

    public RandomSequenceOfUnique( long seedBase,  long seedOffset)
    {
        this.seedBase = seedBase;
        m_index = permuteQPR(seedBase);// + Long.parseLong("682f0161", 16));
        m_intermediateOffset = permuteQPR(seedOffset);// + Long.parseLong("46790905", 16));
    }

    public long next()
    {   
        //System.out.println("next = " + res);
        return permuteQPR((permuteQPR(m_index++) + m_intermediateOffset)) ^ Long.parseLong("5bf235", 16);
    }

    public String nextBase26(){
        return Long.toString(next(), 26);
    }

/*
    public static void main(String[] args){
        long seed = 22484L;
        long seed2 = 58098L;

        RandomSequenceOfUnique runId = new RandomSequenceOfUnique(seed, seed + 1);
        RandomSequenceOfUnique userId = new RandomSequenceOfUnique(seed2, seed2 + 1);

        System.out.println(seed);

        for(int i = 0; i < 4; i++){
            System.out.println(Long.toString(runId.next(), 26) + " " + Long.toString(userId.next(), 26));
        }
    }
*/
}