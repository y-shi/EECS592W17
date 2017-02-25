:- dynamic focusOn/2.
goodAt(X, cs) :- university(X), wealthy(X), goodAt(X, engr).
goodAt(X, engr) :- university(X), focusOn(X, engr).
goodAt(X, football) :- university(X), focusOn(X, football).
wealthy(X) :- university(X), goodAt(X, football), focusOn(X, engr).
goodAt(X, football) :- university(X), beat(X, Y, football), university(Y), goodAt(Y, football).
% goodAt(X, football) :- goodAt(Y, football), university(Y), beat(X, Y, football), university(X).
university(someUniversity).
beat(someUniversity, michigan, football).
university(michigan).
university(mit).
university(osu).
university(alabama).
focusOn(alabama, football).
focusOn(mit, engr).
focusOn(osu, football).
focusOn(michigan, football).
beat(michigan, osu, football).