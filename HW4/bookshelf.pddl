;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Bookshelf domain
(define (domain bookshelf-domain)
  (:requirements :strips)
  
  (:predicates
      (Clear ?x)
      (On ?x ?y)
      (Unread ?x)
      (Book ?x) )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Define the Move and Read actions below
;; See other pddl files for examples
  (:action Move
    :parameters (?X ?Y ?Z)
    :precondition (and (Clear ?X) (On ?X ?Y) (Clear ?Z) (not (= ?X ?Z)))
    :effect (and (On ?X ?Z) (not (On ?X ?Y)) (Clear ?Y) (not (Clear ?Z)))
  )
  (:action Read
    :parameters (?X)
    :precondition (and (Book ?X) (Clear ?X) (Unread ?X))
    :effect (not (Unread ?X))
  )
	 
) ;;; Close parenthesis for domain definition

 
;;; Initial problem state
(define (problem bookshelf-organize)
  (:domain bookshelf-domain)
  (:objects Sh0 Sh1 Sh2 RN B P MRT)

  ;;; Define :init and :goal here
  ;;; See other pddl files for examples
  (:init (On MRT P) (On P B) (On B RN) (On RN Sh0)
      (Clear MRT) (Clear Sh1) (Clear Sh2)
      (Book RN) (Book P) (Book B) (Book MRT)
      (Unread MRT) (Unread P) (Unread B) (Unread RN))
  (:goal (and (Clear RN) (On RN MRT) (On MRT P) (On P B) (On B Sh0)
      (not (Unread RN)) (not (Unread B)) (not (Unread P)) (not (Unread MRT))))

  (:length (:serial 20) (:parallel 20)))
