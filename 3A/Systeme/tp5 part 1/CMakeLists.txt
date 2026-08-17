cmake_minimum_required(VERSION 3.6)
project(tp5)

set(CMAKE_C_STANDARD 11)

set(SOURCE_FILES macros-thread.h ping-pong-sem-thread.c semaphore-moniteur.c semaphore-moniteur.h testsem-thread.c tprintf.c tprintf.h)
add_executable(tp5 ${SOURCE_FILES})