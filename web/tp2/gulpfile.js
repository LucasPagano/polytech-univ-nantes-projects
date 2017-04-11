'use strict';

/*
 * Lancement dans un terminal dédié (laisser le tourner indéfiniment) :
 * node_modules/.bin/gulp
 *
 * la tâche 'lint' vérifie la syntaxe des fichiers js au lancement et à chaque
 * modification d'un fichier.
 *
 */

var gulp = require('gulp'),
    cache = require('gulp-cached'),
    eslint = require('gulp-eslint'),
    watch = require('gulp-watch');

var inputPaths = {
    JavaScript: './*.js',
};

gulp.task('lint', function () {
    return gulp
        .src(inputPaths.JavaScript)
        .pipe(cache('lint'))
        .pipe(eslint({
            'extends': 'eslint:recommended',
            'envs': [
                'es6',
                'node',
            ],
            // documentation des règles eslint :
            // http://eslint.org/docs/rules
            'rules': {
                'no-warning-comments': ['warn', {terms: ['todo', 'fixme', 'xxx'], location: 'anywhere'}],

                'no-console': 'off',

                'strict': ['error', 'global'],

                'indent': ['error', 4, {'SwitchCase': 1}],

                'brace-style': ['error', 'stroustrup'],

                'semi': ['error', 'always'],
                'no-extra-semi': 'error',
                'semi-spacing': ['error', {'before': false, 'after': true}],

                'keyword-spacing': ['error', {'before': true, 'after': true}],

                'no-trailing-spaces': 'error',

                'no-lonely-if': 'error',
                'key-spacing': 'error',
                'comma-spacing': 'error',
                'comma-dangle': ['error', 'always-multiline'],
                'space-infix-ops': ['error', {'int32Hint': true}],
                'array-bracket-spacing': ['error', 'never'],
                'object-curly-spacing': 'error',
                'space-before-function-paren': ['error', {'anonymous': 'always', 'named': 'never'}],
                'wrap-iife': ['error', 'outside'],
                'no-implied-eval': 'error',
                'quotes': ['error', 'single', {'avoidEscape': true}],

                'vars-on-top': 'error',
                'no-undef': 'error',
                'no-unused-vars': 'error',

                'eqeqeq': 'error',
                'no-plusplus': 'error',
                'no-constant-condition': ['error', {'checkLoops': false}],
                'no-eval': 'error',
                'no-extra-bind': 'error',
            },
        }))
        .pipe(eslint.format());
});

gulp.src('*.js')
    .pipe(watch('*.js', {usePolling: true}, function () {
        gulp.start('lint');
    }));

gulp.task('watch', function () {
    //gulp.watch(inputPaths.JavaScript, ['lint']);
});

gulp.task('default', ['lint', 'watch']);

// vim:set et sw=4:
