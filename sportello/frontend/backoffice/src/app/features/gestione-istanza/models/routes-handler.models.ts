export class RoutesHandler
{
    public forbiddenRoutes: string[];
    public defaultRoute: string;

    constructor()
    {
        this.forbiddenRoutes = [];
        this.defaultRoute = "";
    }
} 