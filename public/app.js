const QuestionOwner = ({ name, avatar }) => (  
    <div className="owner">
      <p>{name}</p>
      <img src={avatar} alt="Image of {name}"/>
    </div>
  )


const Question = ({ link, title, owner }) => (
  console.log(owner),
  <div className="question">
    <div className="qa-pair">
      <h2>
        <a href={link}>{title}</a>
      </h2>           
        <QuestionOwner name={owner.display_name} avatar={owner.profile_image}/>
    </div>
  </div>
);

const Questions = ({ questions }) => (
  <div className="questions">
    {
      !questions ?
        <p className="noData">Sorry, no data! Try again later!</p>
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
