const QuestionOwner = ({ name, avatar }) => (  
<div>
    <div>
      <p>{name}</p>
      <img src={avatar} alt="Image of {name}"/>
    </div>
  </div>
  )


const Question = ({ link, title, owner }) => (
  <div className="question">
    <div className="qa-pair">
      <h2>
        <a href={link}>{title}</a>
      </h2>           
        <QuestionOwner key={index} name={owner.displayName} avatar={owner.avatar}/>
    </div>
  </div>
);

const Questions = ({ questions }) => (
  <div className="questions">
    {
      !questions ?
        <p>Sorry, no data!</p>
      :
        questions.map((question, index) => (
          <Question title={question.title} link={question.link} key={index}  owner={question.owner}/>
        ))
    }
  </div>
);

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      data: undefined
    };
  }

  componentDidMount() {
    axios.get(this.props.url)
      .then(function ({ data }) {
        this.setState({
          data: data.items
        });
        console.log(data);
      }.bind(this))
      .catch(function (error) {
        console.error(error);
      }.bind(this));
  }

  render() {
    return (
      <div className="app">
        <h1>Here are the questions</h1>
        <Questions questions={this.state.data} />
      </div>
    )
  }
}

ReactDOM.render(
  <App url="/api/v1/reactjs/questions"/>,
  document.getElementById('content')
);
