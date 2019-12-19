package com.shatteredpixel.cursedpixeldungeon.levels;

public class Layouts {

    //32X32
    private static final int W = Terrain.WALL;
    private static final int Z = Terrain.HIGH_GRASS;
    private static final int D = Terrain.DOOR;
    private static final int L = Terrain.LOCKED_DOOR;
    private static final int Q = Terrain.EMPTY; //for readability

    //private static final int T = Terrain.INACTIVE_TRAP;

    private static final int E = Terrain.EMPTY;
    //private static final int X = Terrain.EXIT;

    private static final int M = Terrain.WALL_DECO;
    //private static final int P = Terrain.PEDESTAL;
    private static final int U = Terrain.STATUE;
    private static final int S = Terrain.SECRET_DOOR;


    public static final int[] SAFE_ROOM_DEFAULT =	{
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,  Q,  Q,  Q,  Q,  Q, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	Z, 	Z,  Z, 	W,  Q, 	Z, 	Z, 	Z,  Q, 	W, 	Z, 	Z, 	Z, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	Z, 	Z,  Z, 	D,  Q, 	Z, 	E, 	Z,  Q, 	D, 	Z, 	Z, 	Z, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	Z,  Z, 	Z, 	W,  Q, 	Z, 	Z, 	Z,  Q, 	W, 	Z, 	Z, 	Z, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,  Q,  Q,  Q,  Q,  Q, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
            W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W

    };

}
