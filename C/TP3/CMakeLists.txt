cmake_minimum_required(VERSION 3.6)
project(TP3)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c Liste.c Liste.h)
add_executable(TP3 ${SOURCE_FILES})