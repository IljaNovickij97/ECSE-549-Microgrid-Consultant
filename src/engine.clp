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


(defrule MoreRE
    
	?CurrentMC<-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (< ?CurrentREI ?minRE)) 
    ?SigRE <- (Sigmas)
    ?FIRE <- (Firing (MoreRE ?x&:(eq ?x TRUE))(DONE ?y&:(eq ?y FALSE)))
    =>
    (modify ?SigRE (Ssolar (/ (- ?minRE ?CurrentREI)?CurrentREI)))
    (modify ?SigRE (Sess (/ (- ?minRE ?CurrentREI) (* ?CurrentREI 2))))
    (modify ?FIRE (MoreRE FALSE))
    (printout t "MoreRE FIRED: ")
    (printout t "The new SolarSigma: "?SigRE.Ssolar crlf)
    (printout t "The new ESSSigma: "?SigRE.Sess crlf)
    
)			

        
     
/*(defrule LessRE
    
	 
	(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    (UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (> ?CurrentREI ?minRE))
    (test (> ?CurrentLCOE ?maxLCOE)) 
     ?SigRE <- (Sigmas)
    =>
    (modify ?SigRE (Ssolar(- ?SigSolar.Ssolar 0.01)))
    (modify ?SigRE (Sess(- ?SigSolar.Sess 0.01)))			
               
)*/

(defrule LessCo2
	?CurrentMC<-(Microgrid (REI ?CurrentREI)(CO2 ?CurrentCO2)(LCOE ?CurrentLCOE)) 
    ?UR <-(UserReq (minRE ?minRE)(maxCO2 ?maxCO2)(maxLCOE ?maxLCOE))
    (test (> ?CurrentCO2 ?maxCO2)) 
    ?FIRE <- (Firing (LessCO2 ?x&:(eq ?x TRUE))(DONE ?y&:(eq ?y FALSE)))
    
     ?SigRE <- (Sigmas)
    
    =>
    (modify ?SigRE (Sgen(- ?SigRE.Sgen 0.01)))
    (printout t "LessCo2 FIRED: ")
    (printout t "The new Genset Sigma: "?SigRE.Sgen crlf)
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
        (modify ?SigRE (Sgen(+ ?SigRE.Sgen 0.01))))   
    
    (modify ?FIRE (LessLCOE FALSE))
    
    (printout t "Less LCOE FIRED: " crlf)
    (printout t "The new Genset Sigma: "?SigRE.Sgen crlf)
    (printout t "The new Solar Sigma: "?SigRE.Ssolar crlf)
    (printout t "The new ESS Sigma: "?SigRE.Sess crlf)
   
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
    (printout t "Requirements Are met" crlf)
)
    

;(watch all)
(reset)
(run)

