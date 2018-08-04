package aspects;

import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import larva.*;
public aspect _asp_Script0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Script0.initialize();
}
}
after ( LiftController lc,int floorNumber) returning (Object lifts) : (execution(* LiftController.getClosestStationaryLifts(..)) && target(lc) && args(floorNumber) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){

_cls_Script0 _cls_inst = _cls_Script0._get_cls_Script0_inst();
_cls_inst.lc = lc;
_cls_inst.floorNumber = floorNumber;
_cls_inst.lifts = lifts;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*getClosest*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*getClosest*/);
}
}
before ( int floorNumber,Lift lift) : (call(* Lift.setFloor(..)) && target(lift) && args(floorNumber) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){

_cls_Script0 _cls_inst = _cls_Script0._get_cls_Script0_inst();
_cls_inst.floorNumber = floorNumber;
_cls_inst.lift = lift;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*setFloor*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*setFloor*/);
}
}
before ( LiftController __1,int floorNumber,Lift lift) : (call(* LiftController.moveLift(..)) && target(__1) && args(lift,floorNumber) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){

_cls_Script0 _cls_inst = _cls_Script0._get_cls_Script0_inst();
_cls_inst.__1 = __1;
_cls_inst.floorNumber = floorNumber;
_cls_inst.lift = lift;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*moveLift*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*moveLift*/);
}
}
before ( LiftController __0,int floorNumber) : (call(* LiftController.callLiftToFloor(..)) && target(__0) && args(floorNumber) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){

_cls_Script0 _cls_inst = _cls_Script0._get_cls_Script0_inst();
_cls_inst.__0 = __0;
_cls_inst.floorNumber = floorNumber;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*floorSummon*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*floorSummon*/);
}
}
}