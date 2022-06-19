package net.optifine.entity.model.anim;

import optifine.Config;

public class ModelVariableUpdater
{
    private final String modelVariableName;
    private final String expressionText;
    private ModelVariable modelVariable;
    private IExpression expression;

    public boolean initialize(IModelResolver mr)
    {
        this.modelVariable = mr.getModelVariable(this.modelVariableName);

        if (this.modelVariable == null)
        {
            Config.warn("Model variable not found: " + this.modelVariableName);
            return false;
        }
        else
        {
            try
            {
                ExpressionParser expressionparser = new ExpressionParser(mr);
                this.expression = expressionparser.parse(this.expressionText);
                return true;
            }
            catch (ParseException parseexception)
            {
                Config.warn("Error parsing expression: " + this.expressionText);
                Config.warn(parseexception.getClass().getName() + ": " + parseexception.getMessage());
                return false;
            }
        }
    }

    public ModelVariableUpdater(String modelVariableName, String expressionText)
    {
        this.modelVariableName = modelVariableName;
        this.expressionText = expressionText;
    }

    public void update()
    {
        float f = this.expression.eval();
        this.modelVariable.setValue(f);
    }
}
