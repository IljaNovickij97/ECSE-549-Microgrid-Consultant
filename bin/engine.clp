(deftemplate UserReq
	(slot minRE)
	(slot maxCO2)
	(slot maxLCOE)
)

(deftemplate Microgrid
    (slot REI)
    (slot CO2)
    (slot LCOE)
    )

(deftemplate Sigmas
    
    (slot Ssolar)
    (slot Sess)
    (slot Sgen)
    )

(deftemplate Firing
	(slot MoreRE)
	(slot LessCO2)
	(slot LessLCOE)
    (slot DONE)
)

(deftemplate LoadMatching
	(slot Generation)
    (slot Load)
    (slot NotMatched)    
)

(defrule Matching
	?LM <- (LoadMatching (Generation ?G)(Load ?L)(NotMatched ?NM&:(eq ?NM FALSE)))
    ?CurrentMC <-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR  <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))    
    ?FIRE <- (Firing)
    ?SigRE <- (Sigmas)
    
    (test (< ?G (- ?L 1)))
    
    =>
    
    (modify ?FIRE (LessCO2 FALSE)(MoreRE FALSE)(LessLCOE FALSE))
    (modify ?LM (NotMatched TRUE))
    (if (< ?CurrentREI ?UR.minRE) then   	
        (modify ?SigRE (Ssolar (/ (- ?L ?G) ?L)))
   		(modify ?SigRE (Sess (/ (- ?L ?G) (* ?L 2))))
        ;(printout t "The new SolarSigma: "?SigRE.Ssolar crlf)
    	;(printout t "The new ESSSigma: "?SigRE.Sess crlf)
        
     else 
        (modify ?SigRE (Sgen (/ (- ?L ?G) ?L)))
        ;(printout t "The new Genset Sigma: "?SigRE.Sgen crlf)
        )

    ;(printout t "Load is not Matched" crlf)  
)

(defrule MoreRE
    
	?CurrentMC<-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (< ?CurrentREI ?minRE)) 
    ?SigRE <- (Sigmas)
    ?FIRE <- (Firing (MoreRE ?x&:(eq ?x TRUE))(DONE ?y&:(eq ?y FALSE)))
    =>
    (modify ?SigRE (Ssolar (/ (- ?minRE ?CurrentREI)?CurrentREI)))
    (modify ?SigRE (Sess (/ (- ?minRE ?CurrentREI) (* ?CurrentREI 2))))
    (if (< ?SigRE.Ssolar 0.01)
        then
        (modify ?SigRE (Ssolar 0.01))
        )
    (if (< ?SigRE.Sess 0.005)
        then
        (modify ?SigRE (Sess 0.005))
        )
    (modify ?FIRE (MoreRE FALSE))
    ;(printout t "MoreRE FIRED: ")
    ;(printout t "The new SolarSigma: "?SigRE.Ssolar crlf)
    ;(printout t "The new ESSSigma: "?SigRE.Sess crlf)
    
)			

(defrule LessCo2
	?CurrentMC<-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (> ?CurrentCO2 ?maxCO2)) 
    ?FIRE <- (Firing (LessCO2 ?x&:(eq ?x TRUE))(DONE ?y&:(eq ?y FALSE)))
    
     ?SigRE <- (Sigmas)
    
    =>
    (modify ?SigRE (Sgen(- ?SigRE.Sgen 0.01)))
    ;(printout t "LessCo2 FIRED: ")
    ;(printout t "The new Genset Sigma: "?SigRE.Sgen crlf)
    (modify ?FIRE (LessCO2 FALSE))
    		
)

(defrule LessLCOE
	
   
    ?CurrentMC<-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (> ?CurrentLCOE ?maxLCOE))
    ?FIRE <- (Firing (LessLCOE ?x&:(eq ?x TRUE))(DONE ?y&:(eq ?y FALSE)))
    
     ?SigRE <- (Sigmas)
    
    =>
    (if (< ?CurrentREI ?UR.minRE) then   	
        (modify ?SigRE (Sgen(- ?SigRE.Sgen 0.01)))
    	
     else
        (modify ?SigRE (Ssolar(- ?SigRE.Ssolar 0.01)))
    	(modify ?SigRE (Sess(- ?SigRE.Sess 0.01)))
        (modify ?SigRE (Sgen(- ?SigRE.Sgen 0.01))))   
    
    (modify ?FIRE (LessLCOE FALSE))
    
    ;(printout t "Less LCOE FIRED: " crlf)
    ;(printout t "The new Genset Sigma: "?SigRE.Sgen crlf)
    ;(printout t "The new Solar Sigma: "?SigRE.Ssolar crlf)
    ;(printout t "The new ESS Sigma: "?SigRE.Sess crlf)
   
    )

(defrule RequirementsMet
    ?MC <-(Microgrid (REI ?CurrentJ)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE))
    ?UR <- (UserReq (minRE ?MinJ)(maxCO2 ?MaxCO2)(maxLCOE ?MaxLCOE))
    ?FIRE <-(Firing (DONE ?y&:(eq ?y FALSE)))
    (test (> ?CurrentJ ?MinJ))
    (test (< ?CurrentCO2 ?MaxCO2))
    (test (< ?CurrentLCOE ?MaxLCOE))
    
    =>
    
    (modify ?FIRE (MoreRE FALSE)(LessCO2 FALSE)(LessLCOE FALSE)(DONE TRUE))
    ;(printout t "Requirements Are met" crlf)
)
    

;(watch all)
(reset)
(run)

