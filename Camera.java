public class Camera {

    public static Vector VRV = new Vector(1, 0, 0);
    public static Vector VUV = new Vector(0, 1, 0);
    public static Vector VRP = new Vector(0 ,0, -1000);
    public static Vector center = new Vector(320, 320, 50);
    public static Vector VPN;

    public static Vector getVPN(){
        VPN = VRV.cross(VUV);
        VPN.normalise();
        return VPN;
    }

    public static Vector getVRP(Vector a, double distance){
        VRP = center.sub(VPN.mul(distance));
        return VRP;
    }

   

    public static void rotateVRV(double degree){
        double x = (VRV.x * Math.cos(Math.toRadians(degree))) - (VRV.z * Math.sin(Math.toRadians(degree)));
        double z = (VRV.x * Math.sin(Math.toRadians(degree))) + (VRV.z * Math.cos(Math.toRadians(degree)));
        VRV.x = x;
        VRV.z = z;
        VRV.normalise();
        getVPN();
        VUV= VPN.cross(VRV);
    }

    public static void rotateVUV(double degree){
        VRP.y += 10 * degree;
        VPN= center.sub(VRP);
        VPN.normalise();
        VUV = VPN.cross(VRV);
        

    }

    public static Vector getCamOrigin(int w, int h){

        return (VRP.sub(VRV.mul(w/2))).add(VUV.mul(h/2));
    }


    
}
