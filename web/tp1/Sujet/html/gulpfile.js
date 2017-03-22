var cssvalidate = require('gulp-w3c-css');
var htmlvalidate = require('gulp-html-validator');
var bs = require('browser-sync').create();

var path = require('path');
var gulp = require('gulp');
var gutil = require('gulp-util');
var map = require('map-stream')

var cssSrcPath = path.join(__dirname, './*.css');
var htmlSrcPath = path.join(__dirname, './*.html');

gulp.task('validatecss', function() {
  bs.reload();
  gulp.src(cssSrcPath)
  .pipe(cssvalidate())
    .pipe(map(function(file, done) {
      console.log("============== CSS ==================");
      if (file.contents.length == 0) {
        console.log('Success: ' + file.path);
        console.log(gutil.colors.green('No errors or warnings\n'));
      } else {
        var results = JSON.parse(file.contents.toString());
        results.errors.forEach(function(error) {
          console.log('Error: ' + file.path + ': line ' + error.line);
          console.log(gutil.colors.red(error.message) + '\n');
        });
        results.warnings.forEach(function(warning) {
          console.log('Warning: ' + file.path + ': line ' + warning.line);
          console.log(gutil.colors.yellow(warning.message) + '\n');
        });
        console.log(results.errors.length + " error - " + results.warnings.length + " warnings\n");
      }
      done(null, file);
    }));
});

gulp.task('validatehtml', function() {
  bs.reload();
  gulp.src(htmlSrcPath)
  .pipe(htmlvalidate())
    .pipe(map(function(file, done) {
      console.log("============== HTML ==================");
      var results = JSON.parse(file.contents.toString());
      if (results.messages.length == 0) {
        console.log('Success: ' + file.path);
        console.log(gutil.colors.green('No errors or warnings\n'));
      } else {
        var nbErrors=0;
        var nbWarnings=0;
        results.messages.forEach(function(message) {
          if( !message.lastLine )
            message.lastLine = "non spécifiée";
          console.log(message.type + ": " + file.path + ': line ' + message.lastLine);
          if( message.type == "error"){
            nbErrors++;
            console.log(gutil.colors.red(message.message) + '\n');
          }
          else if( message.type == "warning" ){
            nbWarnings++;
            console.log(gutil.colors.orange(message.message) + '\n');
          }
          else {
            console.log(message.message + '\n');
          }
        });
        console.log(nbErrors + " error - " + nbWarnings + " warnings\n");
      }
      done(null, file);
    }));
});

gulp.task('browser-sync', function() {
    bs.init({
        server: {
            baseDir: "./"
        }
    });
});

gulp.task('watch', function() {
  gulp.watch(htmlSrcPath, ['validatehtml']);
  gulp.watch(cssSrcPath, ['validatecss']);
});

gulp.task('validate', ['validatehtml', 'validatecss']);

// The default task (called when you run `gulp` from cli)
gulp.task('default', ['watch','browser-sync']);
