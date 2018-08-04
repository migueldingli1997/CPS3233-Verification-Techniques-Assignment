package larva;


import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_Script2 implements _callable{

public static LinkedHashMap<_cls_Script2,_cls_Script2> _cls_Script2_instances = new LinkedHashMap<_cls_Script2,_cls_Script2>();

_cls_Script0 parent;
public static int currFloor;
public static Shaft s1;
public Shaft s;
int no_automata = 1;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Script2( Shaft s) {
parent = _cls_Script0._get_cls_Script0_inst();
this.s = s;
}

public void initialisation() {
}

public static _cls_Script2 _get_cls_Script2_inst( Shaft s) { synchronized(_cls_Script2_instances){
_cls_Script2 _inst = new _cls_Script2( s);
if (_cls_Script2_instances.containsKey(_inst))
{
_cls_Script2 tmp = _cls_Script2_instances.get(_inst);
 return _cls_Script2_instances.get(_inst);
}
else
{
 _inst.initialisation();
 _cls_Script2_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Script2)
 && (s == null || s.equals(((_cls_Script2)o).s))
 && (parent == null || parent.equals(((_cls_Script2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_Script2_instances){
_performLogic_invar2(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_Script2[] a = new _cls_Script2[1];
synchronized(_cls_Script2_instances){
a = _cls_Script2_instances.keySet().toArray(a);}
for (_cls_Script2 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_Script2_instances){
_cls_Script2_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_invar2 = 32;

public void _performLogic_invar2(String _info, int... _event) {

_cls_Script0.pw.println("[invar2]AUTOMATON::> invar2("+s + " " + ") STATE::>"+ _string_invar2(_state_id_invar2, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_invar2==32){
		if (1==0){}
		else if ((_occurredEvent(_event,24/*animateUp*/)) && (currFloor ==s .numFloors -1 )){
		
		_state_id_invar2 = 31;//moving to state bad
		_goto_invar2(_info);
		}
		else if ((_occurredEvent(_event,26/*animateDn*/)) && (currFloor ==0 )){
		
		_state_id_invar2 = 31;//moving to state bad
		_goto_invar2(_info);
		}
}
}

public void _goto_invar2(String _info){
_cls_Script0.pw.println("[invar2]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_invar2(_state_id_invar2, 1));
_cls_Script0.pw.flush();
}

public String _string_invar2(int _state_id, int _mode){
switch(_state_id){
case 31: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 32: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
ArrayList<Lift> getBestLifts(Lift[] lifts, int floor, Map<Lift,Integer> destinations) {

final ArrayList<Lift> result = new ArrayList<Lift>();

// Search for moving lifts which will pass through the floor
for (Lift lift : lifts) {
if (destinations.containsKey(lift) && (
(lift.getFloor() <= floor && destinations.get(lift) >= floor) ||
(lift.getFloor() >= floor && destinations.get(lift) <= floor)
)) {
result.add(lift);
}
}

// Search for closest stationary lifts
int distance = -1;
while (result.size() == 0) {
distance++;
for (Lift lift : lifts) {
if (!lift.isMoving() && lift.distanceFromFloor(floor) == distance) {
result.add(lift);
}
}
}

return result;
}
}